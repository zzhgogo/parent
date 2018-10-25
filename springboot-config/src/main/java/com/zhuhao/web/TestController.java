package com.zhuhao.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisShardInfo;

@RestController
public class TestController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/test")
    public Object test1(){
        redisTemplate.opsForValue().set("name", "朱昊");
        return redisTemplate.opsForValue().get("name");
    }


}
