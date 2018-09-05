package com.zhu.base.DistributedLock;

import redis.clients.jedis.Jedis;
import java.util.Collections;


public class RedisDistributedLock {

    private Jedis jedis;

    /**
     * Lock key path.
     */
    private String lockKey;

    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 100000;
    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 60 * 60 * 1000;


    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";



    public RedisDistributedLock(Jedis jedis, String lockKey, String clientId) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.clientId = clientId;
    }

    public RedisDistributedLock(Jedis jedis, String lockKey, String clientId, int expireMsecs) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.expireMsecs = expireMsecs;
        this.clientId = clientId;
    }

    public RedisDistributedLock(Jedis jedis, String lockKey, String clientId, int expireMsecs, int timeoutMsecs) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.expireMsecs = expireMsecs;
        this.timeoutMsecs = timeoutMsecs;
        this.clientId = clientId;
    }



    public void lock() {
        if (this.tryLock()){

        }else {
            // 等待超时
        }
    }

    public boolean tryLock() {
        int timeout = timeoutMsecs;
        while (timeout > 0){
            boolean success = this.setNXPX(lockKey, clientId, expireMsecs);
            if(success){
                return true;
            }
            timeout  = timeout - DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            try {
                Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void unlock() {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(clientId));
        jedis.close();
    }


    public boolean setNXPX(String lockKey, String clentId, int expireTime) {
        String result = jedis.set(lockKey, clentId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    /**
     * @return lock key
     */
    public String getLockKey() {
        return lockKey;
    }

}
