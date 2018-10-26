package com.zzh.gogo.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: zhuhao
 * @Date: 2018/10/26 上午11:21
 * @Description:
 */
@Component
public class SpringEventListener implements ApplicationListener{

    /**
     * 日志记录对象
     */
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextRefreshedEvent){
            log.info("ContextRefreshedEvent happen");
        }else if(event instanceof ContextStartedEvent){
            log.info("ContextStartedEvent happen");
        }else if(event instanceof ContextStoppedEvent){
            log.info("ContextStoppedEvent happen");
        }else if(event instanceof ContextClosedEvent){
            log.info("ContextClosedEvent happen");
        }
    }

}
