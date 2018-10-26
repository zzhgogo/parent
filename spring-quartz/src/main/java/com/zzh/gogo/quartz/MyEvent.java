package com.zzh.gogo.quartz;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: zhuhao
 * @Date: 2018/10/26 上午11:31
 * @Description:
 */
public class MyEvent extends ApplicationEvent{

    public MyEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "my event";
    }
}
