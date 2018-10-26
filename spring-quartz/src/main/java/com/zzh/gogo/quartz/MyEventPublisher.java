package com.zzh.gogo.quartz;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @Author: zhuhao
 * @Date: 2018/10/26 上午11:32
 * @Description:
 */
@Component
public class MyEventPublisher implements ApplicationEventPublisherAware{

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void publish(){
        MyEvent ce = new MyEvent(this);
        publisher.publishEvent(ce);

    }
}
