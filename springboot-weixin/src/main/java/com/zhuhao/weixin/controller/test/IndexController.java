package com.zhuhao.weixin.controller.test;

import com.zhuhao.utils.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("getClientId")
    public String getIp(){
        return WebUtils.getClentIpAdrress();
    }
}
