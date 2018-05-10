package com.zhuhao.utils;

import com.zhuhao.weixin.message.WeixinMessage;

public class WeixinResponseEncoder {
    private final static String XML_START = "<xml>";
    private final static String ELEMENT_TOUSERNAME = "<ToUserName><![CDATA[%s]]></ToUserName>";
    private final static String ELEMENT_FROMUSERNAME = "<FromUserName><![CDATA[%s]]></FromUserName>";
    private final static String ELEMENT_CREATETIME = "<CreateTime><![CDATA[%d]]></CreateTime>";
    private final static String ELEMENT_MSGTYPE = "<MsgType><![CDATA[%s]]></MsgType>";
    private final static String XML_END = "</xml>";
    public static  String encode(WeixinMessage weixinMessage){
        StringBuilder content = new StringBuilder();
        content.append(XML_START);
        content.append(String.format(ELEMENT_TOUSERNAME, weixinMessage.getFromUserName()));
        content.append(String.format(ELEMENT_FROMUSERNAME, weixinMessage.getToUserName()));
        content.append(String.format(ELEMENT_CREATETIME, System.currentTimeMillis() / 1000l));
        content.append(String.format(ELEMENT_MSGTYPE, weixinMessage.getMsgType()));
        content.append(String.format("<Content><![CDATA[%s]]></Content>", "你好"));
        content.append(XML_END);
        return content.toString();
    }
}
