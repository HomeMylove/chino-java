package com.homemylove.core.load;

import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;

abstract public class BasePlugin {
    public abstract boolean run(RequestBody requestBody, Robot robot);
}
