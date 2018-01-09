package com.zzh.gogo.rabbit.controller;

import com.zzh.gogo.rabbit.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DemoController {


    @Autowired
    private Producer producer;


    private String queueId;

    @RequestMapping("demo/index")
    public String index(){
        producer.sendDirectQueue("123456");
//        try {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("data", "hello rabbitmq");
//            // 注意：第二个属性是 Queue 与 交换机绑定的路由
//            producer.sendQueue(queueId + "_exchange", queueId + "_patt", map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "index";
    }
}
