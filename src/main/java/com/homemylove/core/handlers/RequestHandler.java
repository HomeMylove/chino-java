package com.homemylove.core.handlers;

import com.homemylove.core.load.RobotFactory;
import com.homemylove.core.load.RunPlugin;
import com.homemylove.core.load.impl.Robot;
import com.homemylove.core.reqfactory.RequestBody;
import com.homemylove.core.reqfactory.RequestFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 返回任意值以阻止重复上报
        OutputStream os = exchange.getResponseBody();
        String res = "ok";
        byte[] bytes = res.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type:", "text/html;charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        os.write(bytes);
        os.flush();

        // 获取信息
        InputStream body = exchange.getRequestBody();
        BufferedReader br = new BufferedReader(new InputStreamReader(body, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = br.readLine())!=null){
            sb.append(line);
        }
        String req = sb.toString();
        RequestBody requestBody = RequestFactory.createRequest(req);
        Robot robot = RobotFactory.getRobot();

        Map<String, RunPlugin> plugins = robot.getPlugins();
        for (RunPlugin plugin : plugins.values()) {
           if(plugin.run(requestBody, robot)){
               break;
           }
        }
        br.close();
        os.close();
    }
}
