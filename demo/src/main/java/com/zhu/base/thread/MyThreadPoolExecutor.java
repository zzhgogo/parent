package com.zhu.base.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class MyThreadPoolExecutor  extends ThreadPoolExecutor{

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Long endTime = System.nanoTime();
        Thread thread = Thread.currentThread();
        //System.out.println(String.format("%s执行耗时：%d", thread.getName(), endTime-startTime.get()));

    }

    public static void main(String[] args){
        ThreadPoolExecutor threadPoolExecutor = new MyThreadPoolExecutor(1, 2000, 2, TimeUnit.SECONDS, new SynchronousQueue());
        List<Integer> list = new ArrayList<>();
        for (int i =0; i < 109 ; i++){
            list.add(Integer.valueOf(i));
        }
        List<CompletableFuture<Double>> futures = list.stream().map(m->CompletableFuture.supplyAsync(()-> Math.pow(2, m), threadPoolExecutor)).collect(Collectors.toList());
        List<Double> list1 = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        list1.stream().forEach(System.out::println);
        System.out.println(list1.size());


    }


}
