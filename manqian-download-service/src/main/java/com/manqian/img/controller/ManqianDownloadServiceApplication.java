package com.manqian.img.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ManqianDownloadServiceApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder()
                .sources(ManqianDownloadServiceApplication.class)
                .run(args);
    }
}
