package com.zzh.gogo.quartz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");

        MyService myService = (MyService) applicationContext.getBean("myService");

        MyEventPublisher myEventPublisher = applicationContext.getBean(MyEventPublisher.class);

        myEventPublisher.publish();
        countDownLatch.await();

    }

}
