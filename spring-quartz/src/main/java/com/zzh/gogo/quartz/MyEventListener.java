package com.zzh.gogo.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: zhuhao
 * @Date: 2018/10/26 上午11:34
 * @Description:
 */
@Component
public class MyEventListener implements ApplicationListener<MyEvent>{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        log.info(myEvent.toString());
    }
}
