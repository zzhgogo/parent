package com.zhuhao.controller;

import com.alibaba.fastjson.JSON;
import com.zhuhao.vo.SocketMessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/2")
    public String index1() {
        return "index2";
    }


    @MessageMapping("/hello1")
    public void toTopic1(SocketMessageVo msg) {
        logger.info("接收路径[/app/hello1]:{} 订阅路径[/topic/hello1]：{}", JSON.toJSON(msg), JSON.toJSON(msg));
        this.simpMessagingTemplate.convertAndSend("/topic/hello1",msg.getName()+","+msg.getMsg());
    }

    //与toTop1()功能相同
    @MessageMapping("/hello2")
    @SendTo("/topic/hello2")
    public Object toTopic2(SocketMessageVo msg) {
        logger.info("接收路径[/app/hello2]:{} 订阅路径[/topic/hello2]：{}", JSON.toJSON(msg), JSON.toJSON(msg));
        return msg;
    }

    @MessageMapping("/message1")
    @SendToUser("/message1")
    public Object toUser(SocketMessageVo msg) {
        logger.info("接收路径[/app/message1]:{} 订阅路径[/topic/message1]：{}", JSON.toJSON(msg), JSON.toJSON(msg));
        //this.simpMessagingTemplate.convertAndSendToUser("123","/message",msg.getName()+msg.getMsg());
        return msg;
    }

//    @MessageMapping("/message2")
//    public void toUser2(SocketMessageVo msg) {
//        logger.info("接收路径[/app/message2]:{} 订阅路径[/user/123456/message2]：{}", JSON.toJSON(msg), JSON.toJSON(msg));
//        this.simpMessagingTemplate.convertAndSendToUser("123456","/message",msg.getName()+msg.getMsg());
//    }

    @RequestMapping("/sendMsg")
    @ResponseBody
    public String sendMsg(HttpSession session){
        SocketMessageVo msg = new SocketMessageVo();msg.setName("sessionId");msg.setMsg(session.getId());
        logger.info("测试发送消息：随机消息" +session.getId());
        // 发送到 /user/123456/message2 订阅路径当中
        simpMessagingTemplate.convertAndSendToUser("123456","/message2", JSON.toJSONString(msg));//
        return "success";
    }
}
