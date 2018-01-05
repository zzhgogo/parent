package com.zhu.base.thread;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;

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
    public void bb() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> futureList = Lists.newArrayList();
        for(int i = 0 ; i < 100 ; i++){
            final int t = i;
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep((long) Math.random()*10000);
                   // System.out.println(Thread.currentThread().getName());
                    return "Thread:"+t;
                }
            });
            futureList.add(future);
        }
        executorService.shutdown();
        for (Future<String> fs : futureList) {
            try {
                String result = fs.get();
                System.out.println(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
