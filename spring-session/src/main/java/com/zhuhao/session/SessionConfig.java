package com.zhuhao.session;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class SessionConfig {

    private final static Logger logger = LoggerFactory.getLogger(SessionConfig.class);

    @Bean("sessionRedisConnectionFactory")
    @Primary
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName("127.0.0.1");
        factory.setPort(6379);
        factory.setDatabase(8);
        return factory;
    }

}