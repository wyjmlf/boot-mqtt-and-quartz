package com.zh.util;

public class WxUrl {

    public static final String APPID="wx04f55a5b50c30eb5";
    public static final String SECRET="06fc917d06821d0eac7ff85feeacdd98";


    /**
     * 发送信息给用户
     */
    public static final String CUSTPMER_MESSAGE_URL="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    /**
     * 获取token
     */
    public static final String TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WxUrl.APPID+"&secret="+WxUrl.SECRET;

}
