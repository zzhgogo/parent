package com.zhuhao.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@SpringBootApplication
public class SpringbootEsApplication {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder()
                .sources(SpringbootEsApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
