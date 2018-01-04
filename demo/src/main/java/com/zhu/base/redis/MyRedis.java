package com.zhu.base.redis;


import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Jedis jedis = new Jedis("127.0.0.1");
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
}
