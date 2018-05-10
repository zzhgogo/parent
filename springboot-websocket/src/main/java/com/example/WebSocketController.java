package com.example;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class WebSocketController {
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/1")
    public String index1() {
        return "index";
    }


    @MessageMapping("/hello")
    @SendTo("/topic/hello")//会把方法的返回值广播到指定主题（“主题”这个词并不合适）
    public Object toTopic(SocketMessageVo msg) {
        System.out.println(msg.getName()+","+msg.getMsg());
        //this.simpMessagingTemplate.convertAndSend("/topic/hello",msg.getName()+","+msg.getMsg());
        return msg;
    }

    @MessageMapping("/message")
    @SendToUser("/message")//把返回值发到指定队列（“队列”实际不是队列，而是跟上面“主题”类似的东西，只是spring在SendTo的基础上加了用户的内容而已）
    public Object toUser(SocketMessageVo msg) {
        System.out.println(msg.getName()+","+msg.getMsg());
        //this.simpMessagingTemplate.convertAndSendToUser("123","/message",msg.getName()+msg.getMsg());
        return msg;
    }

    @RequestMapping("/sendMsg")
    @ResponseBody
    public String sendMsg(HttpSession session){
        System.out.println("测试发送消息：随机消息" +session.getId());
        this.simpMessagingTemplate.convertAndSendToUser("123","/message","后台具体用户消息");
        return "success";
    }
}
