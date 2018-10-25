package com.zhuhao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//https://www.cnblogs.com/GoodHelper/p/7078381.html
//https://www.cnblogs.com/nevermorewang/p/7274217.html
//https://blog.csdn.net/yingxiake/article/details/51213060
//https://blog.csdn.net/valenon/article/details/45892377
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	//https://www.cnblogs.com/mr-wuxiansheng/p/7103567.html
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {

		//订阅Broker名称(表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息)
		config.enableSimpleBroker("/topic","/user");

		//全局使用的订阅前缀（客户端订阅路径上会体现出来）(指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀)
		config.setApplicationDestinationPrefixes("/app");//@MessageMapping

		//点对点使用的订阅前缀（客户端订阅路径上会体现出来，不设置的话，默认也是/user/）(指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀)）
		config.setUserDestinationPrefix("/user");

	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/webServer").setAllowedOrigins("*").withSockJS();
	}

}
