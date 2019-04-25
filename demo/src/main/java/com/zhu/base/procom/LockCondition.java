package com.zhu.base.procom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuhao on 2018/3/6
 */
public class LockCondition {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Condition producerCondition = lock.newCondition();
        Condition consumerCondition = lock.newCondition();

        Resource2 resource = new Resource2(lock,producerCondition,consumerCondition);

        //生产者线程
        ProducerThread2 producer1 = new ProducerThread2(resource);

        //消费者线程
        ConsumerThread2 consumer1 = new ConsumerThread2(resource);
        ConsumerThread2 consumer2 = new ConsumerThread2(resource);
        ConsumerThread2 consumer3 = new ConsumerThread2(resource);

        producer1.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }
}
