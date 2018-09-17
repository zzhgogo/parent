package com.zhuhao.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;
import redis.clients.jedis.JedisShardInfo;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AppController extends BaseController{

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/test")
    public Object test(){
        JedisShardInfo jedisShardInfo;
        redisTemplate.opsForValue().set("name", "朱昊");
        return redisTemplate.opsForValue().get("name");
    }


}
