package com.zhuhao.weixin.listenter;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.DebugMessageHandler;
import com.foxinmy.weixin4j.spring.SpringBeanFactory;
import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;
import com.foxinmy.weixin4j.util.AesToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Weixin4jStartListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(Weixin4jStartListener.class);

    private static String handlerPackage = "com.zhuhao.weixin.hander";

    @Value("${wx.port}")
    private int port;

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.token}")
    private String token;

    @Value("${wx.aesKey}")
    private String aesKey;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("启动微信服务器...");
        startWxServer(contextRefreshedEvent.getApplicationContext());

    }

    /**
     * 启动weixin4j服务
     */
    public void startWxServer(ApplicationContext applicationContext) {
        new Thread(() -> {
            try {
                //new WeixinServerBootstrap(new AesToken(appId, token, aesKey)) // 指定开发者token信息。
                new WeixinServerBootstrap(new AesToken("wxa4cc0a202b65477c", "weixin4j", "29d8103ec273d5af766ed8ba7eaec315")) // 指定开发者token信息。
                        .handlerPackagesToScan(handlerPackage) // 扫描处理消息的包。
                        .resolveBeanFactory(new SpringBeanFactory(applicationContext))// 声明处理消息类由Spring容器去实例化。
                        .addHandler(DebugMessageHandler.global) // 当没有匹配到消息处理时输出调试信息，开发环境打开。
                        .openAlwaysResponse() // 当没有匹配到消息处理时输出空白回复(公众号不会出现「该公众号无法提供服务的提示」)，正式环境打开。
                        .startup(port); // 绑定服务的端口号，即对外暴露(微信服务器URL地址)的服务端口。
            } catch (WeixinException e) {
                logger.info("微信服务器启动失败...");
            }
        }).start();

    }
}
