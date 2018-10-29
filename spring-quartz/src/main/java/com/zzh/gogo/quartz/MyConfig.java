package com.zzh.gogo.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhuhao
 * @Date: 2018/10/27 10:38 AM
 * @Description:
 */
@Configuration
public class MyConfig {

    @Bean
    public String string(){
        return new String("朱昊");
    }
}
