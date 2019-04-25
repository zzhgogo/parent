package com.zhuhao.consts;

public interface WeixinUrl {

    /**
     * 获取accessToken
     */
    String GET_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 生成带参数的二维码
     */
    String GER_ERCDOE_WITH_PARAM_URL =  "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

    String GER_ERCDOE_BY_TICKET = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";


}
