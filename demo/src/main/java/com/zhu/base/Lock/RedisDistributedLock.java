package com.zhu.base.Lock;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisDistributedLock {


    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 1;

    public static Integer index = 0;

    private Jedis jedis;
    /**
     * Lock key path.
     */
    private String lockKey;
    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 100000;
    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 60 * 60 * 1000;

    private volatile boolean locked = false;

    public RedisDistributedLock(Jedis jedis, String lockKey) {
        this.jedis = jedis;
        this.lockKey = lockKey;
    }

    public RedisDistributedLock(Jedis jedis, String lockKey, int expireMsecs) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.expireMsecs = expireMsecs;
    }

    public RedisDistributedLock(Jedis jedis, String lockKey, int expireMsecs, int timeoutMsecs) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.expireMsecs = expireMsecs;
        this.timeoutMsecs = timeoutMsecs;
    }

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.select(3);

        String lock_key ="redis_lock_key_"+System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    RedisDistributedLock lock = new RedisDistributedLock(jedis, lock_key);
                    try {
                        if (lock.lock()) {
                            System.out.println("last: " + RedisDistributedLock.index);
                            RedisDistributedLock.index++;
                        }
                    } finally {
                        //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
                        //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
                        lock.unlock();
                    }
                }
            });
        }
    }

    public synchronized boolean lock() {

        int timeout = timeoutMsecs;

        boolean flag =true;

//        while (timeout >= 0) {
        while (flag) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires); //锁到期时间
            if (setNX(lockKey, expiresStr)) {
                locked = true;
                return true;
            }
            String currentValueStr = this.get(lockKey); //redis里的时间
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
                // lock is expired
                String oldValueStr = this.getSet(lockKey, expiresStr);
                //获取上一个锁到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受

                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    // lock acquired
                    locked = true;
                    flag =false;
                    return true;
                }
            }
            timeout = timeout - DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            /*
                延迟100 毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                使用随机的等待时间可以一定程度上保证公平性
             */
            try {
                Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private boolean setNX(final String key, final String value) {
        long result = jedis.setnx(key, value);
        return result == 0 ? false : true;
    }

    private String get(final String key) {
        Object obj = jedis.get(key);
        return obj != null ? obj.toString() : null;
    }

    private String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = jedis.getSet(key, value);
        } catch (Exception e) {
            System.out.println("setNX redis error, key");
        }
        return obj != null ? (String) obj : null;
    }

    /**
     * @return lock key
     */
    public String getLockKey() {
        return lockKey;
    }

    /**
     * Acqurired lock release.
     */
    public synchronized void unlock() {
        if (locked) {
            jedis.del(lockKey);
            locked = false;
        }
    }
}
