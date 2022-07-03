package com.homemylove.core.load;

import com.sun.net.httpserver.HttpHandler;

public interface RobotInter {

    void init();

    void init(String hostname, Integer port);

    void bind(String path,HttpHandler httpHandler);

    void start();

}
