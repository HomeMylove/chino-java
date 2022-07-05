package com.homemylove.core.handlers;

import com.homemylove.core.load.*;
import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;
import com.homemylove.core.reqfactory.RequestFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


public class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 返回任意值以阻止重复上报
        OutputStream os = exchange.getResponseBody();
        String res = "ok";
        byte[] bytes = res.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type:", "text/html;charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        os.write(bytes);
        os.flush();

        // 获取信息
        InputStream body = exchange.getRequestBody();
        BufferedReader br = new BufferedReader(new InputStreamReader(body, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String req = sb.toString();
        RequestBody requestBody = RequestFactory.createRequest(req);
        Robot robot = RobotFactory.getRobot();

        String message = requestBody.getMessage();
        if (message == null) {
            return;
        }
        if (message.startsWith("帮助") || message.toLowerCase().startsWith("help")) {
            handleHelp(requestBody, robot);
        } else if (message.startsWith("关闭") || message.toLowerCase().startsWith("ban")
                || message.startsWith("开启") || message.toLowerCase().startsWith("allow")
        ) {
            handleControl(requestBody, robot);
        } else {
            Map<String, BasePlugin> plugins = robot.getPlugins();
            for (BasePlugin plugin : plugins.values()) {
                if (plugin instanceof CommonPlugin) {
                    CommonPlugin commonPlugin = (CommonPlugin) plugin;
                    Help help = commonPlugin.getHelp(requestBody, robot);
                    if(!help.isPass()){
                        continue;
                    }
                }
                if (plugin.run(requestBody, robot)) {
                    break;
                }
            }
        }
        br.close();
        os.close();
    }


    public void handleHelp(RequestBody requestBody, Robot robot) {
        System.out.println("进入帮助。。。。");
        String message = requestBody.getMessage();
        String groupId = requestBody.getGroupId();
        message = message.toLowerCase().replace("帮助", "").replace("help", "").trim();
        // 全部帮助
        Map<String, BasePlugin> plugins = robot.getPlugins();
        // 帮助内容
        StringBuilder sb = new StringBuilder();
        System.out.println("正常");
        sb.append(robot.getName()).append("可以实现以下功能:");
        int index = 1;
        for (Map.Entry<String, BasePlugin> entry : plugins.entrySet()) {
            BasePlugin value = entry.getValue();
            // 判断是不是 CommonPlugin
            if (value instanceof CommonPlugin) {
                CommonPlugin commonPlugin = (CommonPlugin) value;
                Help help = commonPlugin.getHelp(requestBody, robot);
                if (help == null || help.getName() == null) continue;
                if (help.getName().equals(message) || message.equals(index + "")) {
                    sb = new StringBuilder();
                    sb.append("功能:==").append(help.getName()).append("==");
                    if (!help.isPass()) {
                        sb.append("(本群已禁用)");
                    }
                    Map<String, String> helpMap = help.getHelpMap();
                    for (Map.Entry<String, String> helpEntry : helpMap.entrySet()) {
                        sb.append("\n").append(helpEntry.getKey()).append(":\t").append(helpEntry.getValue());
                    }
                    robot.sendGroupMessage(groupId, sb.toString());
                    return;
                }
                String circle = help.isPass() ? "● " : "○ ";
                sb.append("\n").append(index).append("\t").append(circle).append(help.getName());
                index++;
            }
        }
        sb.append("\n").append("详情请输入 帮助+id");
        robot.sendGroupMessage(groupId, sb.toString());
    }

    public void handleControl(RequestBody requestBody, Robot robot) {
        // 验证是否有操作权限
        String userId = requestBody.getUserId();
        List<String> superusers = robot.getSuperusers();
        if (!superusers.contains(userId)) {
            return;
        }
        String groupId = requestBody.getGroupId();
        String message = requestBody.getMessage();
        String banName = null;
        boolean flag = true;  // true 关闭
        if (message.startsWith("关闭") || message.toLowerCase().startsWith("ban")) {
            banName = message.toLowerCase().replace("关闭", "").replace("ban", "");
        } else {
            banName = message.toLowerCase().replace("开启", "").replace("allow", "");
            flag = false;
        }

        Map<String, BasePlugin> plugins = robot.getPlugins();
        Integer index = 1; // 索引
        for (Map.Entry<String, BasePlugin> entry : plugins.entrySet()) {
            BasePlugin plugin = entry.getValue();
            if (plugin instanceof CommonPlugin) {
                CommonPlugin commonPlugin = (CommonPlugin) plugin;
                Help help = commonPlugin.getHelp(requestBody, robot);
                if (help == null || help.getName() == null) continue;
                if (help.getName().equals(banName) || banName.equals(index + "")) {
                    List<String> banList = commonPlugin.getBanList();
                    Message reqMsg = new Message(groupId);
                    if (flag) {
                        if (!banList.contains(groupId)) {
                            banList.add(groupId);
                            reqMsg.setMsg("关闭" + help.getName() + "成功!!!");
                        } else {
                            reqMsg.setMsg(help.getName() + "已关闭!!!");
                        }
                    } else {
                        if (banList.remove(groupId))
                            reqMsg.setMsg("开启" + help.getName() + "成功!!!");
                        else {
                            reqMsg.setMsg(help.getName() + "未关闭!!!");
                        }
                    }
                    robot.sendGroupMessage(reqMsg);
                    return;

                }
                index++;
            }
        }
    }
}
