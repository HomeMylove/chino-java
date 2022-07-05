package com.homemylove.chino.plugins.common;

import com.homemylove.chino.pojo.Echo;
import com.homemylove.chino.service.EchoService;
import com.homemylove.core.load.Help;
import com.homemylove.core.load.Message;
import com.homemylove.core.load.CommonPlugin;
import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EchoPlugin extends CommonPlugin {

    private final EchoService echoService = null;

    @Override
    public boolean run(RequestBody requestBody, Robot robot) {
        System.out.println("监测 echo");
        String message = requestBody.getMessage();
        String groupId = requestBody.getGroupId();
        Echo echo = null;
        if (message.startsWith("echo")) {
            addEcho(requestBody, robot);
            return true;
        } else if (message.startsWith("update")) {
            updateEcho(requestBody, robot);
            return true;
        } else if (message.startsWith("delete")) {
            deleteEcho(requestBody, robot);
            return true;
        } else if (message.startsWith("show")) {
            showEcho(requestBody, robot);
            return true;
        } else if ((echo = echoService.getEcho(groupId, message)) != null) {
            robot.sendGroupMessage(new Message(groupId, echo.getAnswer()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Help getHelp(RequestBody requestBody, Robot robot) {
        Help help = new Help("echo");
        Map<String, String> helpMap = help.getHelpMap();
        helpMap.put("echo", "echo A B");
        helpMap.put("update", "update 10 C D");
        helpMap.put("delete", "delete 10");
        helpMap.put("show", "show | show keyword");

        help.setPass(isPass(requestBody.getGroupId()));
        return help;
    }

    public void addEcho(RequestBody requestBody, Robot robot) {
        String rawStr = requestBody.getMessage().replace("echo", "");
        String regStr = "\\s(\\S+?)\\s";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(rawStr);
        String question = null;
        if (matcher.find()) {
            question = matcher.group(1);
        }
        if (question == null)
            return;
        String answer = rawStr.replace(question, "").trim();
        String groupId = requestBody.getGroupId();
        String userId = requestBody.getUserId();
        Message msg = null;
        Echo echo = new Echo(0, groupId, userId, question, answer);

        if (echoService.isFull(groupId, userId)) {
            Echo minEcho = echoService.getMinEcho(groupId, userId);
            echo.setId(minEcho.getId());
            echoService.updateEcho(echo);
            String m = robot.getName() + "记住啦,以后你可以对我说:\"" + question + "\"\n" +
                    "但是" + robot.getName() + "只能记住10句话哦，这句话替换了你的" + minEcho.getQuestion() + "\n"
                    + "替换其他语句请使用 update";
            msg = new Message(groupId, m);
        } else {
            echoService.addEcho(echo);
            msg = new Message(groupId, robot.getName() + "记住啦,以后你可以对我说:\"" + question + "\"");
        }
        msg.setAtId(userId);
        robot.sendGroupMessage(msg);
    }

    public void updateEcho(RequestBody requestBody, Robot robot) {
        String rawStr = requestBody.getMessage().replace("update", "");
        String regStr = "\\s(\\d+?)\\s";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(rawStr);
        String id = null;
        if (matcher.find()) {
            id = matcher.group(1);
        }
        if (id == null)
            return;
        rawStr = rawStr.replace(id, "");
        regStr = "\\s(\\S+?)\\s";
        pattern = Pattern.compile(regStr);
        matcher = pattern.matcher(rawStr);
        String question = null;
        if (matcher.find()) {
            question = matcher.group(1);
        }
        if (question == null)
            return;
        String answer = rawStr.replace(question, "").trim();
        String groupId = requestBody.getGroupId();
        String userId = requestBody.getUserId();
        Message msg = null;
        if (echoService.hasEcho(Integer.parseInt(id), groupId, userId)) {
            Echo echo = new Echo(Integer.parseInt(id), groupId, userId, question, answer);
            echoService.updateEcho(echo);
            msg = new Message(groupId, robot.getName() + "记住啦,以后你可以对我说:\"" + question + "\"");
        } else {
            msg = new Message(groupId, "这句话不属于你,请使用 show 确认id");
        }
        msg.setAtId(userId);
        robot.sendGroupMessage(msg);
    }

    public void deleteEcho(RequestBody requestBody, Robot robot) {
        String message = requestBody.getMessage();
        String groupId = requestBody.getGroupId();
        String userId = requestBody.getUserId();
        try {
            Integer id = Integer.parseInt(message.replace("delete", "").trim());
            Message msg = null;
            if (echoService.hasEcho(id, groupId, userId)) {
                Echo delEcho = echoService.getEchoById(id);
                echoService.deleteEcho(id);
                msg = new Message(groupId, "删除成功！！\n" + robot.getName() + "不会回应你的" + "\"" + delEcho.getQuestion() + "\"");
            } else {
                msg = new Message(groupId, "删除失败!!\r\n因为这句话不属于你");
            }

            msg.setAtId(userId);
            robot.sendGroupMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message(groupId, "格式错误");
            msg.setAtId(userId);
            robot.sendGroupMessage(msg);
        }
    }

    public void showEcho(RequestBody requestBody, Robot robot) {
        String message = requestBody.getMessage();
        String groupId = requestBody.getGroupId();
        String userId = requestBody.getUserId();
        String content = message.replace("show", "").trim();
        List<Echo> echoList = null;
        if (content.equals("")) {
            echoList = echoService.getEchoList(groupId, userId);
        } else {
            echoList = echoService.getEchoListByKeyword(groupId, content);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("智乃可以回答以下问题:\n").append("id\t\t\t\tQ\t\t\t\t\t\tA\n");

        for (int i = 0; i < echoList.size(); i++) {
            Echo echo = echoList.get(i);
            sb.append(echo.getId()).append(" ")
                    .append(echo.getQuestion()).append("---->").
                    append(echo.getAnswer());
            if (i != echoList.size() - 1) {
                sb.append("\n");
            }
        }
        robot.sendGroupMessage(new Message(groupId, sb.toString(), userId));

    }
}
