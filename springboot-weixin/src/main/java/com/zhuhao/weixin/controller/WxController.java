package com.zhuhao.weixin.controller;


import com.zhuhao.utils.*;

import com.zhuhao.weixin.hander.WeixinMessageDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @Author: zhuhao
 * @Date: 2018/10/25 下午12:02
 * @Description:
 */

@Controller
public class WxController {


    private final static Logger logger = LoggerFactory.getLogger(WxController.class);

    @Autowired
    private WeixinMessageDispatcher weixinMessageDispatcher;

    /**
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return
     */
    @RequestMapping(value = "sign", method = RequestMethod.GET)
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

    @RequestMapping(value = "sign", method = RequestMethod.POST)
    @ResponseBody
    public String sign(HttpServletRequest request) throws Exception{
       return weixinMessageDispatcher.doDispatch(request);
    }




}