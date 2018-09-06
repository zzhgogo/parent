package com.zhu.base.sync;

import com.zhu.base.DistributedLock.RedisDistributedLock;
import com.zhu.base.DistributedLock.ZookeeperDistributedLock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LockTest {

    public Integer index = new Integer(0);

    @Test
    public void t1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(900000);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 900000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    ZookeeperDistributedLock lock = new ZookeeperDistributedLock("127.0.0.1:2181", "test4");
                    try {
                        lock.lock();
                        index++;
                        FileUtils.write(new File("/data/z.log"), String.valueOf(index)+System.lineSeparator(), "utf-8" ,true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                        countDownLatch.countDown();
                    }

                }
            });

        }
        countDownLatch.await();
        System.out.println(stopWatch.getTime());
    }

    @Test
    public void t2() throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.select(3);
        jedis.set("index", "0");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(900000);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 900000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis = new Jedis("127.0.0.1");
                    jedis.select(3);
                    RedisDistributedLock lock = new RedisDistributedLock(jedis, "redis_lock_1", UUID.randomUUID().toString());
                    try{
                        lock.lock();
                        index++;
                    }finally {
                        lock.unlock();
                        try {

                            FileUtils.write(new File("/data/r.log"), String.valueOf(index)+System.lineSeparator(), "utf-8" ,true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                    }

                }
            });
        }
        countDownLatch.await();
        System.out.println(stopWatch.getTime());

    }

    @Test
    public void t3() throws InterruptedException {

        Jedis jedis = new Jedis("127.0.0.1");
        jedis.select(3);
        ExecutorService executorService = Executors.newCachedThreadPool();
        //CountDownLatch countDownLatch = new CountDownLatch(300);

        for (int i = 0; i < 60000; i++) {
            ZookeeperDistributedLock lock = new ZookeeperDistributedLock("127.0.0.1:2181", "test3");
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.lock();
                        Integer index = Integer.valueOf(jedis.get("index"));
                        index++;
                        jedis.set("index", String.valueOf(index));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                        // countDownLatch.countDown();
                    }

                }
            });

        }
        //countDownLatch.await();
    }

    @Test
    public void t4() throws InterruptedException {

        Jedis jedis = new Jedis("127.0.0.1");
        jedis.select(3);
        ExecutorService executorService = Executors.newCachedThreadPool();
        //CountDownLatch countDownLatch = new CountDownLatch(300);

        for (int i = 0; i < 30000; i++) {
            ZookeeperDistributedLock lock = new ZookeeperDistributedLock("127.0.0.1:2181", "test3");
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.lock();
                        Integer index = Integer.valueOf(jedis.get("index"));
                        index++;
                        jedis.set("index", String.valueOf(index));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                        // countDownLatch.countDown();
                    }

                }
            });

        }
        //countDownLatch.await();
    }


}
