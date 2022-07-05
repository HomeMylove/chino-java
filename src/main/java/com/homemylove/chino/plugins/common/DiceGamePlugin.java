package com.homemylove.chino.plugins.common;

import com.homemylove.core.load.CommonPlugin;
import com.homemylove.core.load.Help;
import com.homemylove.core.load.Message;
import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.MemberInfo;
import com.homemylove.core.reqfactory.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceGamePlugin extends CommonPlugin {
    @Override
    public boolean run(RequestBody requestBody, Robot robot) {
        System.out.println("监测 dice game");
        String message = requestBody.getMessage();
        String groupId = requestBody.getGroupId();
        String userId = requestBody.getUserId();
        if (!message.contains("掷骰子")) {
            return false;
        }
        List<String> oppoList = new ArrayList<>();
        oppoList.add(userId);
        String oppo = message.replace("掷骰子", "").trim();
        if (oppo.equals("")) {
            oppoList.add(robot.getSelfId());
        } else {
            Pattern pattern = Pattern.compile("\\[CQ:at,qq=(\\d+?)]");
            Matcher matcher = pattern.matcher(oppo);
            while (matcher.find()) {
                oppo = matcher.group(1);
                if (!oppoList.contains(oppo))
                    oppoList.add(oppo);
            }
        }
        List<String> winners = new ArrayList<>();
        Integer max = 0;
        Integer point;
        StringBuilder sb = new StringBuilder();

        for (String player : oppoList) {
            point = (int) (Math.random() * 6) + 1;
            if (player.equals(robot.getSelfId()))
                sb.append(robot.getName()).append("掷出了").append(point).append("\n");
            else
                sb.append("[CQ:at,qq=").append(player).append("]你掷出了").append(point).append("\n");

            if (point > max) {
                max = point;
                winners.clear();
                winners.add(player);
            } else if (point == max) {
                winners.add(player);
            }
        }
        MemberInfo groupMemberInfo = null;
        for (int i = 0; i < winners.size(); i++) {
            String winner = winners.get(i);
            if (winner.equals(robot.getSelfId()))
                sb.append(robot.getName());
            else {
                groupMemberInfo = robot.getGroupMemberInfo(groupId, winner);
                sb.append(groupMemberInfo.getNickname());
            }
            if (i < winners.size() - 1) {
                sb.append("、");
            }
        }
        sb.append("获得了胜利");
        robot.sendGroupMessage(groupId, sb.toString());
        return true;
    }

    @Override
    public Help getHelp(RequestBody requestBody, Robot robot) {
        Help help = new Help("掷骰子");
        Map<String, String> helpMap = help.getHelpMap();
        helpMap.put("掷骰子", "掷骰子 | 掷骰子@** | @**掷骰子");

        help.setPass(isPass(requestBody.getGroupId()));
        return help;
    }
}
