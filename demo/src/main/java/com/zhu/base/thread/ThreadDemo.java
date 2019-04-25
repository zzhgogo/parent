package com.zhu.base.thread;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Author: as
 * Time:  2017/11/16 10:24
 * Description:
 */
public class ThreadDemo {

    // https://blog.csdn.net/fwt336/article/details/81530581
    @Test
    public void tt(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        executorService.shutdown();
    }
    @Test
    public void bb() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> futureList = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            Future<String> future = executorService.submit(() -> Thread.currentThread().getName());
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
    public void t1() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<CompletableFuture<String>> completableFutures = Stream.iterate(100, i -> i + 1).limit(1000)
                .map(i -> CompletableFuture.supplyAsync(() -> i.toString(), executorService))
                .collect(Collectors.toList());
        List<String> strings = completableFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println(strings);


    }

    // 参考 http://www.jcodecraeer.com/a/chengxusheji/java/2017/1226/9011.html, https://www.cnblogs.com/zedosu/p/6665306.html
    @Test
    public void t2(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(35, 150, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
        List<Integer> list = Stream.iterate(100, i -> i + 1).limit(1000).collect(Collectors.toList());
        List<CompletableFuture<Integer>> futures = list.stream().map(m -> CompletableFuture.supplyAsync(() -> m, threadPoolExecutor)).collect(Collectors.toList());
        List<Integer> results = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.print(results);
    }

    @Test
    public void t3(){
        StopWatch watch=new StopWatch();
        watch.start();
        watch.stop();
        System.out.print(watch.getTime());
    }



    @Test
    public void t6() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Stream.iterate(0, i -> i + 1).limit(10).forEach(i -> {
            executorService.submit(() -> {
                countDownLatch.countDown();
                System.out.println(i);
            });
        });
        // 10个线程countDown()都执行之后才会释放当前线程,程序才能继续往后执行
        countDownLatch.await();
        System.out.println("Finish");
    }

    @Test
    public void t7() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(100000);
        Stream.iterate(0, i -> i + 1).limit(100000).forEach(i -> {
            executorService.submit(() -> {
                atomicInteger.incrementAndGet();
                countDownLatch.countDown();
            });
        });
        countDownLatch.await();
        System.out.println(atomicInteger.get());

    }

    @Test
    public void t8() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(100000);
        for (int i = 0; i < 100000 ; i++){
            executorService.submit(() -> {
                atomicInteger.incrementAndGet();
                countDownLatch.countDown();
            });
        }
        executorService.shutdown();
//        executorService.shutdownNow();
        countDownLatch.await();

        System.out.println(atomicInteger.get());
    }

    /**
     * 周期执行，延迟执行
     *
     * @throws Exception
     */
    // https://www.cnblogs.com/huhx/p/baseusejavaScheduledExecutorService.html
    // https://www.cnblogs.com/dolphin0520/p/3949310.html
    @Test
    public void t9() throws Exception {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Future handler = executor.submit(() -> {
            for (int i = 0; i < 3000; i++) {
//                System.out.println(i+" running..");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            countDownLatch.countDown();
        });

        // 一秒钟后取消任务
        executor.schedule(() -> {
            boolean isCancel = handler.cancel(false);
            System.out.println("handler 超时..." + isCancel);
        }, 1000, TimeUnit.MILLISECONDS);
        countDownLatch.await();

    }

    @Test
    public void t10() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService poll = Executors.newSingleThreadExecutor();
        Future<Boolean> future = poll.submit(() -> {
            Thread.sleep(1000 * 10);
            System.out.println("任务执行完成");
            countDownLatch.countDown();
            return true;
        });
        try {
            Boolean b = future.get(3, TimeUnit.SECONDS);
            System.out.println(b);
        } catch (InterruptedException e) {
            e.printStackTrace(); //get为一个等待过程，异常中止get会抛出异常
        } catch (ExecutionException e) {
            e.printStackTrace(); //submit计算出现异常
        } catch (TimeoutException e) {
            e.printStackTrace(); //超时异常
            future.cancel(false); //超时后取消任务
        }finally {

        }
        countDownLatch.await();
    }


    @Test
    public void t11() throws InterruptedException {

        ThreadLocal<Long> time = new ThreadLocal<>();
        time.set(System.currentTimeMillis());

        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Stream.iterate(0, i -> i + 1).limit(10).forEach(i -> {
            executorService.submit(()->{
                try {
                    time.set(System.currentTimeMillis());
                    Thread.sleep(10);
                    System.out.println(Thread.currentThread().getName() + " use time: " + (System.currentTimeMillis() - time.get()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });

        });


        countDownLatch.await();
        System.out.println("Main thread use time: " + (System.currentTimeMillis() - time.get()));
    }
}
//start(),sleep(),wait(),yield(),join()