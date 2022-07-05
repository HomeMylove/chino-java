package com.homemylove.core.load.impl;

import com.alibaba.fastjson.JSONObject;
import com.homemylove.core.load.BasePlugin;
import com.homemylove.core.load.Message;
import com.homemylove.core.load.RobotInter;
import com.homemylove.core.reqfactory.MemberInfo;
import com.homemylove.core.utils.HttpRequest;
import com.homemylove.core.utils.JSONUtil;
import com.homemylove.core.utils.StringUtil;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.Executors;

public class Robot implements RobotInter {
    private String name;
    private String selfId;
    private final List<String> superusers = new ArrayList<>();
    private final List<String> nicknames = new ArrayList<>();

    private final Map<String, BasePlugin> plugins = new LinkedHashMap<>();

    private HttpServer httpServer;

    private String listenHost;
    private Integer listenPort;

    private String targetHost;

    private String targetPort;

    private String pluginPosition;

    public Robot() {
    }

    public void sendGroupMessage(String groupId, String msg) {
        String url = targetHost + ":" + targetPort + "/send_group_msg";
        HashMap<String, String> params = new HashMap<>();
        params.put("group_id", groupId);
        params.put("message", URLEncoder.encode(msg));
        params.put("auto_escape", "false");
        HttpRequest.sendGet(url, params);
    }

    public void sendGroupMessage(Message message) {
        String url = targetHost + ":" + targetPort + "/send_group_msg";
        String groupId = message.getGroupId();
        String msg = message.getMsg();
        String img = message.getImg();
        String record = message.getRecord();
        String atId = message.getAtId();

        HashMap<String, String> params = new HashMap<>();
        params.put("group_id", groupId);
        StringBuilder sb = new StringBuilder();
        if (StringUtil.isNotEmpty(atId)) {
            sb.append("[CQ:at,qq=").append(atId).append("]");
        }
        if (StringUtil.isNotEmpty(msg)) {
            sb.append(msg);
            if (StringUtil.isNotEmpty(img)) {
                sb.append("[CQ:image,file=").append(img).append("]");
            }
        } else if (StringUtil.isNotEmpty(img)) {
            sb.append("[CQ:image,file=").append(img).append("]");
        } else if (StringUtil.isNotEmpty(record)) {
            sb.append("[CQ:record,file=").append(img).append("]");
        }
        params.put("message", URLEncoder.encode(sb.toString()));
        params.put("auto_escape", "false");

        HttpRequest.sendGet(url, params);
    }

    public MemberInfo getGroupMemberInfo(String groupId, String userId) {
        try {
            String result = HttpRequest.sendGet(String.format("%s:%s/get_group_member_info?group_id=%s&user_id=%s", targetHost, targetPort, groupId, userId));
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (!jsonObject.get("status").equals("ok")) {
                return null;
            }
            Object data = jsonObject.get("data");
            return JSONUtil.jsonObjToJavaObj((JSONObject) data, MemberInfo.class);
        } catch (Exception e) {
            throw new RuntimeException("获取信息失败", e);
        }


    }


    public String getName() {
        return name;
    }

    public void init() {
        if (listenHost == null || listenPort == null)
            init("127.0.0.1", 5701);
        else {
            init(listenHost, listenPort);
        }
    }

    public void init(String hostname, Integer port) {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(hostname, port), 0);
            httpServer.setExecutor(Executors.newFixedThreadPool(10));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, BasePlugin> getPlugins() {
        return plugins;
    }

    public void bind(String path, HttpHandler httpHandler) {
        httpServer.createContext(path, httpHandler);
    }

    public void start() {
        httpServer.start();
    }

    public String getListenHost() {
        return listenHost;
    }

    public void setListenHost(String listenHost) {
        this.listenHost = listenHost;
    }

    public Integer getListenPort() {
        return listenPort;
    }

    public void setListenPort(Integer listenPort) {
        this.listenPort = listenPort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public List<String> getSuperusers() {
        return superusers;
    }


    public List<String> getNicknames() {
        return nicknames;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public String getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(String targetPort) {
        this.targetPort = targetPort;
    }

    public String getPluginPosition() {
        return pluginPosition;
    }

    public void setPluginPosition(String pluginPosition) {
        this.pluginPosition = pluginPosition;
    }
}
