package com.zhu.base.redis;


import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

public class RedisPool {

    private static JedisPool pool;        //线程池对象
    private static String ADDR = "127.0.0.1"; 	//redis所在服务器地址（案例中是在本机）
    private static int PORT = 6379; 		//端口号
    private static String AUTH = "";		//密码（我没有设置）
    private static int MAX_IDLE = 10;		//连接池最大空闲连接数（最多保持空闲连接有多少）
    private static int TIMEOUT = 10000;		//链接连接池的超时时间#使用连接时，检测连接是否成功
    private static boolean TEST_ON_BORROW = true;	//使用连接时，测试连接是否可用

    static{
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(MAX_IDLE);
        config.setTestOnBorrow(TEST_ON_BORROW);
        pool = new JedisPool(config, ADDR, PORT, TIMEOUT); //新建连接池，如有密码最后加参数
    }

    public static Jedis getJedisResource () {
        try {
            if (pool!=null) {
                Jedis resource = pool.getResource();
                return resource;
            }
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    ///回收连接方法
    public static void returnResource (Jedis used) {

        if(pool!=null){
            pool.returnResource(used);
        }

    }


    public static void main(String[] args){

        Jedis jedisCli = RedisPool.getJedisResource();
        jedisCli.select(7);
        jedisCli.hset("family", "lbq", "65"); //同Redis命令行中的hset操作，如名为family的set不存在，则创建set并放入名为lbq的元素，值为65
        jedisCli.hset("family", "zjz", "62"); //Redis不支持int类型，如不传String则会报错。
        System.out.println(jedisCli.hmget("family","lbq","zjz"));

        Map testMap1 = new HashMap();
        testMap1.put("num1", "1"); //此处同上面，不能传非String型
        testMap1.put("num2", "15");
        testMap1.put("num3", "606");
        testMap1.put("num4", "1024");
        jedisCli.hmset("testMap1", testMap1); //传入整个map对象入redis
        System.out.println(jedisCli.hmget("testMap1", "num1","num2","num3","num4"));

        jedisCli.lpush("Countries", "USA");	//redis对list头的插入元素操作
        jedisCli.lpush("Countries", "UK");
        jedisCli.lpush("Countries", "CHINA");
        System.out.println(jedisCli.lindex("Countries", 0));
        System.out.println(jedisCli.lrange("Countries", 0, 1));
        System.out.println(jedisCli.rpop("Countries"));	  //list末尾弹出元素


        jedisCli.sadd("book", "1", "2");


        System.out.println(jedisCli.setnx("nx", "1"));
        System.out.println(jedisCli.setnx("nxx", "3"));


    }


}
