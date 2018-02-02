package com.zzh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhuhao on 2018/2/2
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    @RequestMapping("/ftl")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("test");
        return modelAndView;
    }
}
