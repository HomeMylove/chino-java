package com.homemylove.core.load;

import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;

import java.util.ArrayList;
import java.util.List;

abstract public class CommonPlugin extends BasePlugin {

    private final List<String> banList = new ArrayList<>();

    private final List<String> perList = new ArrayList<>();

    abstract public Help getHelp(RequestBody requestBody,Robot robot);

    public boolean isPass(String groupId){
        return !(banList.contains(groupId) || (perList.size() > 0 && !perList.contains(groupId)));
    }

    public List<String> getBanList() {
        return banList;
    }

    public List<String> getPerList() {
        return perList;
    }


}
