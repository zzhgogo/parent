package com.zhuhao.weixin;

import com.alibaba.fastjson.JSONObject;
import com.zhuhao.utils.HttpClientUtil;
import com.zhuhao.utils.SignUtil;
import com.zhuhao.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


/**
 * 微信授权控制器
 */
@Controller
public class WxController {

    private final static Logger logger = LoggerFactory.getLogger(WxController.class);

    public static final String AUTH_ACCESS_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

    /** 获取用户授权access_token的url */
    public static final String AUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private  String APP_ID = "wx3ff89b6b9577c419";

    private  String APP_SECRET = "05f62ac73bf46b3ead693329004d0725";

    /**
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return
     */
    @RequestMapping(value = "/async/wxSign", method = RequestMethod.GET)
    @ResponseBody
    public String chectToken(@RequestParam(value = "signature") String signature,
                             @RequestParam(value = "timestamp") String timestamp,
                             @RequestParam(value = "nonce") String nonce,
                             @RequestParam(value = "echostr") String echostr) {
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return "failed!";
    }


    /**
     * 微信回调处理(获取用户openId)
     * @author               朱昊
     * @param code           微信返回，用于获取授权的access token
     * @param state          随机字符，用作校验
     */
    @RequestMapping(value = "/async/wxcb", method = RequestMethod.GET)
    @ResponseBody
    public Object callBack(String code, String state, String backUrl) throws IOException {
        HttpServletResponse response = WebUtils.getResponse();
        if(StringUtils.isBlank(code)){
            response.sendRedirect("");
        }
        String url = String.format(AUTH_ACCESS_TOKEN_URL, APP_ID, APP_SECRET, code);
        String accessTokenRes = HttpClientUtil.doGet(url);
        logger.info("accessTokenRes {} ", accessTokenRes);
        JSONObject resultJson = JSONObject.parseObject(accessTokenRes);
        //授权失败
        if (resultJson.containsKey("errcode")) {
            //跳转错误页面
            response.sendRedirect("");
        }
        String openId = resultJson.getString("open_id");
        logger.info("openId {} ", openId);
        return null;

    }

    /**
     * 微信登录接口
     * @author  朱昊
     * @throws IOException
     */
    @RequestMapping(value = "/async/login", method = RequestMethod.GET)
    @ResponseBody
    public String wxLogin() throws Exception{
        String backUrl = "https://hao.360.cn/";
        HttpServletResponse response = WebUtils.getResponse();
        String baseUrl = "http://ws.toutiao.manqian.cn"+ "/async/wxcb?backUrl=" + URLEncoder.encode(backUrl, "utf-8");
        String url = URLEncoder.encode(baseUrl, "utf-8");
        String auth_url = String.format(AUTH_ACCESS_URL, APP_ID, url);
        response.sendRedirect(auth_url);
        return "";
    }

    /**
     * 微信登录接口
     * @author  朱昊
     * @throws IOException
     */
    @RequestMapping(value = "/async/wxcb/MP_verify_gUETqucX8FSm4NPo.txt")
    @ResponseBody
    public String fff() {

        return "gUETqucX8FSm4NPo";
    }



}
