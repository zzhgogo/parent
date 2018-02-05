package com.zzh.controller.api;

import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuhao on 2018/2/5
 */
@Controller
//类上使用@Api
@Api(value="用户controller",description="用户相关操作")
public class apiController {

    @RequestMapping(value="index",method=RequestMethod.POST)
    @ApiOperation(value="首页",notes="跳转到首页")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String", paramType = "query")
    @ResponseBody
    public Object getIndex(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name","朱昊");
        map.put("date", new Date());
        return map;
    }

}
