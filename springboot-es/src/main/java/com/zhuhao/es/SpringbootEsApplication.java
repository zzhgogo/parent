package com.zhuhao.es;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableDubboConfiguration
public class SpringbootEsApplication {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder()
                .sources(SpringbootEsApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
