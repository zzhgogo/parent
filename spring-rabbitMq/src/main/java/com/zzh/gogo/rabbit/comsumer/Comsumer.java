package com.zzh.gogo.rabbit.comsumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class Comsumer implements MessageListener {

    public void onMessage(Message message) {
        System.out.println("消息消费者 = " + message.toString());
    }
}