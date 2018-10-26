package com.zhu.base.redis;


import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Author: as
 * Time:  2017/9/25 11:33
 * Description: JAVA使用redis
 */
public class MyRedis {

    /**
     * 连接redis
     */
    @Test
    public void testConnection(){
        Jedis jedis  = new Jedis("127.0.0.1");

        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
    }

    /**
     * String(字符串)
     */
    @Test
    public  void testString() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("username", "朱昊");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("username"));
    }

    /**
     *  List(列表)
     */
    @Test
    public void testList(){
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println("连接成功");
        //存储数据到列表中

        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");

        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) {
            System.out.println("列表项为: "+list.get(i));
        }
    }

    /**
     * hashMap(哈希)
     */
    @Test
    public void testMap(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        Map<String, String> map = new HashMap<>();
        map.put("name", "朱昊");
        map.put("age", "23");
        map.put("phone", "18244975178");
        map.put("company", "非凡之星");
        for (Map.Entry<String, String> entry : map.entrySet()){
            jedis.hset("userInfo", entry.getKey(), entry.getValue());
        }
        Map<String, String> map1 = jedis.hgetAll("userInfo");
        for (Map.Entry<String, String> entry : map1.entrySet()){
            System.out.println("userInfo["+entry.getKey()+":"+entry.getValue()+"]");
        }

    }

    @Test
    public void sent(){
        Jedis jedis  = new Jedis("192.168.8.128",6301);
        jedis.auth("123456");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
    }

    public void sentinelTest(){
        Set<String> sentinels = new HashSet<>();
        sentinels.add("192.168.2.195:26301");
        sentinels.add("192.168.2.194:26302");
        JedisSentinelPool jedissentinelPool = new JedisSentinelPool("mymaster", sentinels);
        Jedis jedis = null;
        while(true){

        }
    }
    //测试哨兵
    @Test
    public  void tt() throws Exception {
        // TODO Auto-generated method stub

        Set<String> sentinels = new HashSet<String>();
        sentinels.add("192.168.8.128:26301");
        sentinels.add("192.168.8.128:26302");
        sentinels.add("192.168.8.128:26303");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels);
        Jedis jedis = null;

        while (true) {
            Thread.sleep(1000);

            try {
                jedis = jedisSentinelPool.getResource();

                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String format_now = dateFormat.format(now);

                jedis.set("hello", "world");
                String value = jedis.get("hello");
                System.out.println(format_now + ' ' + value);

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (jedis != null)
                    try {
                        jedis.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
            }
        }

    }

    @Test
    public void jedisPusub() throws InterruptedException {



        RedisSubPubListener listener = new RedisSubPubListener();

        new Thread(()->{
            Jedis jedis  = new Jedis("127.0.0.1",6379);
            jedis.subscribe(listener, "channel");
        }).start();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(()->{
            ExecutorService executorService = Executors.newFixedThreadPool(20);

            for (int i = 10000 ; i < 20000 ; i++){
                int finalI = i;
                executorService.submit(()->{
                    Jedis jedis  = new Jedis("127.0.0.1", 6379);
                    jedis.publish("channel", String.valueOf(finalI));
                    jedis.close();
                    //countDownLatch.countDown();
                });
            }
        }).start();
        countDownLatch.await();


    }
}
