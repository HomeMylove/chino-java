package com.homemylove.core.reqfactory;

import com.alibaba.fastjson.JSONObject;
import com.homemylove.core.utils.JSONUtil;

public class RequestFactory {
    public static RequestBody createRequest(String req){
        JSONObject jsonObject = JSONObject.parseObject(req);
        RequestBody requestBody = JSONUtil.jsonObjToJavaObj(jsonObject, RequestBody.class);
        Object senderObj = jsonObject.get("sender");
        Sender sender = JSONUtil.jsonObjToJavaObj((JSONObject) senderObj, Sender.class);
        requestBody.setSender(sender);
        return requestBody;
    }
}
