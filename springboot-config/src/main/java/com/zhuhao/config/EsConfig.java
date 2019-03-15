package com.zhuhao.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:zhuhao
 * @Description:
 * @Date:
 **/
@Configuration
public class EsConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Value("${es.host}")
//    private String host;
//
//    @Value("${es.tcp.port}")
//    private int port;
//
//    @Bean("client")
//    public Client getClient() {
//        Client client = null;
//        Settings settings = Settings.builder().put("cluster.name", "manqian-search").build();
//        try {
////            client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
//            client = new PreBuiltTransportClient(settings);
//            logger.info("create client success");
//        } catch (Throwable e) {
//            logger.error("获取transportClient异常：", e);
//        }
//
//        return client;
//    }


//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
//
//        return new ElasticsearchTemplate(getClient());
//    }


}
