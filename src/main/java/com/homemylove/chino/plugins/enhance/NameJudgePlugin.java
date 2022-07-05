package com.homemylove.chino.plugins.enhance;

import com.homemylove.core.load.EnhancePlugin;
import com.homemylove.core.load.Message;
import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;
import com.homemylove.core.utils.PinyinUtil;


public class NameJudgePlugin extends EnhancePlugin {
    @Override
    public boolean run(RequestBody requestBody, Robot robot) {
        System.out.println("监测 name judge");
        String message = requestBody.getMessage();
        String groupId = requestBody.getGroupId();
        String userId = requestBody.getUserId();

        String trim = message.trim();
        if (trim.length() >= 2) {
            String f2 = trim.substring(0, 2);
            String[] pinyinArray = PinyinUtil.getPinyinArray(f2);
            // 这里有可能只有一个值
            if(pinyinArray.length < 2){
                return false;
            }
            String w1 = pinyinArray[0];
            String w2 = pinyinArray[1];
            if (
                    ((w1.equals("zhi") || w1.equals("zi")) && w2.equals("nai") && !f2.equals("智乃")) ||
                            (w1.equals("nai") && (w2.equals("zhi") || w2.equals("zi")))
            ) {
                String msg = "我叫" + robot.getName() + "!!!,不叫" + f2 + ",你再这样叫的话我可要生气了ヽ（≧□≦）ノ";
                Message reqMsg = new Message(groupId, msg, userId);
                reqMsg.setImg("mc/sajiao.jpg");
                robot.sendGroupMessage(reqMsg);
                return true;
            }
        }
        return false;
    }
}
