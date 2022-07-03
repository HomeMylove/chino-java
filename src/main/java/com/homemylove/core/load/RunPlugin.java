package com.homemylove.core.load;

import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;

public interface RunPlugin {
    boolean run(RequestBody requestBody, Robot robot);
}
