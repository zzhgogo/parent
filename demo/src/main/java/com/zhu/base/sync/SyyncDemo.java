package com.zhu.base.sync;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyyncDemo {

    public static String LockString = "LockString";

    public static int index = 0;

    @Test
    public void t1() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        int count = 1000000;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i =0; i < count; i++){
            executor.submit(() -> {
                synchronized (SyyncDemo.LockString){
                    index++;
                    latch.countDown();
                }
            });
        }
        latch.await();
        System.out.println("index: "+index);
    }


    @Test
    public void t2() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        int count = 1000000;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i =0; i < count; i++){
            executor.submit(() -> {
                addIndex();
                latch.countDown();
            });

        }
        latch.await();
        System.out.println("index: "+index);
    }

    public static synchronized void addIndex(){
        index++;
    }

    @Test
    public void t3() throws InterruptedException{
        Lock lock = new ReentrantLock();
        ExecutorService executor = Executors.newFixedThreadPool(100);
        int count = 1000000;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i =0; i < count; i++){
            executor.submit(() -> {
                try{
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "获取了锁");
                    index++;
                }finally {
                    latch.countDown();
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + "释放了锁");
                }
            });

        }
        latch.await();
        System.out.println("index: "+index);
    }


}
