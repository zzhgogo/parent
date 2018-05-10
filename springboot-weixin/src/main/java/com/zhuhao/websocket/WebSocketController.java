package com.zhuhao.websocket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
//@EnableScheduling
//public class WebSocketController {
//
//	@Autowired
//	private SimpMessagingTemplate messagingTemplate;
//
//	@GetMapping("/")
//	public String index() {
//		return "index";
//	}
//	@GetMapping("/2")
//	public String index2() {
//		return "index2";
//	}
//
//	@MessageMapping("/send")
//	@SendTo("/topic/send")
//	public SocketMessage send(SocketMessage message) throws Exception {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		message.date = df.format(new Date());
//		return message;
//	}
//
//	@Scheduled(fixedRate = 1000)
//	@SendTo("/topic/callback")
//	public Object callback() throws Exception {
//		// 发现消息
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
//		return "callback";
//	}
//
//
//	/**
//	 * 这里用的是@SendToUser，这就是发送给单一客户端的标志。本例中，
//	 * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message” ,这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行。
//	 * @return
//	 */
//	@MessageMapping("/message")
//	@SendToUser("/message")
//	public Greeting handleSubscribe() {
//		System.out.println("this is the @SubscribeMapping('/marco')");
//		return new Greeting("I am a msg from SubscribeMapping('/macro').");
//	}
//
//	/**
//	 * 测试对指定用户发送消息方法
//	 * @return
//	 */
//	@RequestMapping(path = "/send", method = RequestMethod.GET)
//	@ResponseBody
//	public String send() {
//		messagingTemplate.convertAndSendToUser("123456", "/message", new Greeting("I am a msg from SubscribeMapping('/macro')."));
//		return new String("I am a msg from SubscribeMapping('/macro').");
//	}
//
//	/**
//	 * 表示服务端可以接收客户端通过主题“/app/hello”发送过来的消息，客户端需要在主题"/topic/hello"上监听并接收服务端发回的消息
//	 * @param topic
//	 * @param headers
//	 */
//	@MessageMapping("/hello") //"/hello"为WebSocketConfig类中registerStompEndpoints()方法配置的
//	@SendTo("/topic/greetings")
//	public void greeting(@Header("atytopic") String topic, @Headers Map<String, Object> headers) {
//		System.out.println("connected successfully....");
//		System.out.println(topic);
//		System.out.println(headers);
//	}
//}
