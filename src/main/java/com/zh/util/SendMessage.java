package com.zh.util;

import com.alibaba.fastjson.JSONObject;

public class SendMessage {

    public static void sendtext(String toUser,String content,String access_token) {
        //获取全局token和上面的不一样，上面的token只是网页授权用的

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("touser",toUser);
        jsonObject.put("msgtype","text");
        JSONObject js=new JSONObject();
        js.put("content",content);
        jsonObject.put("text",js);
        String s = HttpClientUtil.doPostJson(WxUrl.CUSTPMER_MESSAGE_URL.replaceAll("ACCESS_TOKEN",access_token),JSONObject.toJSONString(jsonObject));
        System.out.println(access_token);
        System.out.println(jsonObject);
        System.out.println("发送消息回调信息");
        System.out.println(s);
    }
}
