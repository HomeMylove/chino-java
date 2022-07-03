package com.homemylove.chino.plugins;

import com.homemylove.core.load.BotPlugin;
import com.homemylove.core.load.Message;
import com.homemylove.core.load.RunPlugin;
import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;

@BotPlugin(name = "SignInPlugin")
public class SignInPlugin implements RunPlugin {

    public boolean run(RequestBody requestBody, Robot robot){
        String message = requestBody.getMessage();
        String groupId = requestBody.getGroupId();

        if(message.equals("你好")){
            robot.sendGroupMessage(new Message(groupId,"大家好"));
        }

        return true;
    }
}
