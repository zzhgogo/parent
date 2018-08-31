package com.zhu.base.thread;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Author: as
 * Time:  2017/11/16 10:24
 * Description:
 */
public class MultiThreading {

    @Test
    public void tt(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        executorService.shutdown();
    }
    @Test
    public void bb() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> futureList = Lists.newArrayList();
        for(int i = 0 ; i < 100 ; i++){
            final int t = i;
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep((long) Math.random()*100);
                   // System.out.println(Thread.currentThread().getName());
                    return "Thread:"+t;
                }
            });
            futureList.add(future);
        }
//        shutdown() 方法在终止前允许执行以前提交的任务，
//        shutdownNow() 方法阻止等待任务启动并试图停止当前正在执行的任务。在终止时执行程序没有任务在执行，也没有任务在等待执行，并且无法提交新任务。关闭未使用的 ExecutorService 以允许回收其资源。
        executorService.shutdown();
        for (Future<String> fs : futureList) {
            String result = fs.get();
            System.out.println(result);
        }
    }

    // 参考 http://www.jcodecraeer.com/a/chengxusheji/java/2017/1226/9011.html
    @Test
    public void t1() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Integer> list = Lists.newArrayList();
        list.add(1); list.add(2);
        List<CompletableFuture<Integer>> futures = list.stream().map( m -> CompletableFuture.supplyAsync(()->{return m;}, executorService)).collect(Collectors.toList());
        List<Integer> results = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.print(results);

    }
    // 参考 http://www.jcodecraeer.com/a/chengxusheji/java/2017/1226/9011.html
    @Test
    public void t2(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(35, 150, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
        List<Integer> list = Lists.newArrayList();
        list.add(1); list.add(2);
        List<CompletableFuture<Integer>> futures = list.stream().map( m -> CompletableFuture.supplyAsync(()->{return m;}, threadPoolExecutor)).collect(Collectors.toList());
        List<Integer> results = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.print(results);
    }

    @Test
    public void t3(){
        StopWatch watch=new StopWatch();
        watch.start();
        try {
            Thread.sleep(3*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(watch.getTime());
    }

    @Test
    public void t4(){
       String ids = "123_136";

    }

    @Test
    public void t5() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                // FUN1
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                // FUN2
            }
        });

    }
}
//start(),sleep(),wait(),yield(),join()