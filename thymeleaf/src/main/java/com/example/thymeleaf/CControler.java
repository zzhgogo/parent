package com.example.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CControler {

    @RequestMapping("")
    public String index(Model model) throws Exception{
        model.addAttribute("text", "thymeleaf");
        model.addAttribute("url", "www.baidu.com");
        model.addAttribute("baidu", "百度");

        List<Map<String ,Object>> list = new ArrayList<>();
        for (int i = 0 ; i < 10; i++){
            Map<String, Object> map = new HashMap<>();
            map.put("name", "name"+i);
            list.add(map);
        }
        model.addAttribute("list", list);
        return "index";
    }
}
