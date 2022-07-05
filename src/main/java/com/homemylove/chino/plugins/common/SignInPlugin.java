package com.homemylove.chino.plugins.common;

import com.homemylove.core.load.Help;
import com.homemylove.core.load.CommonPlugin;
import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;

public class SignInPlugin extends CommonPlugin {

    public boolean run(RequestBody requestBody, Robot robot){
        System.out.println("监测 sign");
        String message = requestBody.getMessage();
        String groupId = requestBody.getGroupId();

        return false;
    }

    @Override
    public Help getHelp(RequestBody requestBody, Robot robot) {
        return new Help("签到");
    }
}
