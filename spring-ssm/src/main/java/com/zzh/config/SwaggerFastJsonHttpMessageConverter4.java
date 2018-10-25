package com.zzh.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpOutputMessage;
import springfox.documentation.spring.web.json.Json;

/**
 * 解决SpringMVC使用FastJsonHttpMessageConverter时Swagger2失效问题(废弃)
 * Created by zhuhao on 2018/2/5
 */
public class SwaggerFastJsonHttpMessageConverter4 extends FastJsonHttpMessageConverter {

    public SwaggerFastJsonHttpMessageConverter4() {
        super();
        this.getFastJsonConfig().getSerializeConfig().put(Json.class, SwaggerJsonSerializer.instance);
    }


}
