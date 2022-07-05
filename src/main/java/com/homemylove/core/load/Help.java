package com.homemylove.core.load;

import java.util.HashMap;
import java.util.Map;

public class Help {
    private String name;
    // 详细功能
    private final Map<String, String> helpMap = new HashMap<>();

    public Help() {
    }

    public Help(String name) {
        this.name = name;
    }

    public Help(String name, boolean pass) {
        this.name = name;
        this.pass = pass;
    }

    // 是否允许通过
    private boolean pass = true;

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getHelpMap() {
        return helpMap;
    }
}
