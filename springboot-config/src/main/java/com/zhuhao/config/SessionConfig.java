package com.zhuhao.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class SessionConfig {

    private final static Logger logger = LoggerFactory.getLogger(SessionConfig.class);

    @Primary
    @Bean("sessionRedisConnectionFactory")
    @ConfigurationProperties(prefix = "session.redis")
    public JedisConnectionFactory jedisConnectionFactory() {
        logger.info("sessionRedisConnectionFactory init success");
        return new JedisConnectionFactory();
    }

}