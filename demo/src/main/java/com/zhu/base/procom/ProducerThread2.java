package com.zhu.base.procom;

/**
 * Created by zhuhao on 2018/3/6
 */
public class ProducerThread2 extends Thread{

    private Resource2 resource;

    public ProducerThread2(Resource2 resource){
        this.resource = resource;
        setName("生产者");
    }
    public void run(){
        while(true){
            try {
                Thread.sleep((long) (1000 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource.add();
        }
    }

}
