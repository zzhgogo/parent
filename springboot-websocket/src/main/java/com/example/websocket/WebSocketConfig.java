package com.example.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//参考：https://segmentfault.com/a/1190000009038991#articleHeader1，https://www.cnblogs.com/GoodHelper/p/7078381.html
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/my-websocket").withSockJS();
	}


//	@Override
//	protected void configureStompEndpoints(StompEndpointRegistry registry) {
//		registry.addEndpoint("/ws")
//				// 在握手时就获得user，判断是否登录。
//				.addInterceptors(new SessionAuthHandshakeInterceptor())
//				.setHandshakeHandler(new DefaultHandshakeHandler(){
//					@Override
//					protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//						return new MyPrincipal((User) attributes.get("user"));
//					}
//				})
//				.setAllowedOrigins("http://127.0.0.1:8081");
//	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.setInterceptors(new ChannelInterceptorAdapter() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				System.out.println("recv : "+message);
				StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
				//User user = (User)accessor.getSessionAttributes().get("user");
				return super.preSend(message, channel);
			}

		});
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.setInterceptors(new ChannelInterceptorAdapter() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				//System.out.println("send: "+message);
				return super.preSend(message, channel);
			}
		});
	}

}
