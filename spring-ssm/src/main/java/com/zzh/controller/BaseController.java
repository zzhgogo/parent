package com.zzh.controller;


import com.zzh.comom.ResultDto;
import com.zzh.consts.ResultConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Author: D.Yang
 * Email: koyangslash@gmail.com
 * Date: 16/10/9
 * Time: 下午1:37
 * Describe: 基础控制器
 */
public class BaseController {
    /**
     * 获取request
     *
     * @return
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


    /**
     * 获取response
     *
     * @return
     */
    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取session
     *
     * @return
     */
    protected HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    /**
     * 渲染数据
     * @return
     */
    protected ResultDto render(ResultConstant result){
        return new ResultDto(result);
    }

    /**
     * 渲染数据
     * @return
     */
    protected ResultDto render(ResultConstant result, Object data){
        return new ResultDto(result, data);
    }
}
