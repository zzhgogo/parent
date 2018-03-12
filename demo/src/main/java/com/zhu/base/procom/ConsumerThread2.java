package com.zhu.base.procom;

/**
 * Created by zhuhao on 2018/3/6
 */
public class ConsumerThread2 extends Thread{

    private Resource2 resource;
    public ConsumerThread2(Resource2 resource){
        this.resource = resource;
        setName("消费者");
    }
    public void run(){
        while(true){
            try {
                Thread.sleep((long) (1000 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource.remove();
        }
    }

}
