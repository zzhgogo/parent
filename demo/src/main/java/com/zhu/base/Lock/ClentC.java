package com.zhu.base.Lock;

import redis.clients.jedis.Jedis;

public class ClentC {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1");
        jedis.select(3);
        String lock_key = "redis_lock_key_1";
        RedisLock lock = new RedisLock(jedis, lock_key);
        for (int i = 0; i < 300000; i++) {
            try {
                if (lock.lock()) {
                    Integer index = Integer.valueOf(jedis.get("index"));
                    index++;
                    jedis.set("index", String.valueOf(index));
                }
            } finally {
                //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
                //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
                lock.unlock();
            }
        }
    }

}
