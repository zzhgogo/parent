package com.zhu.base.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.junit.Test;

public class DubboDemo {

    @Test
    public void t1(){

        // 普通编码配置方式
        ApplicationConfig application = new ApplicationConfig();
        application.setName("bazinga-consumer");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);        reference.setRegistry(registry);
        reference.setInterface("org.bazinga.service.EasyCommonService");
        reference.setGeneric(true); // 声明为泛化接口

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Object result = genericService.$invoke("helloService", new String[] { "java.lang.String" }, new Object[] { "world" });
        System.out.println(result);


    }



}
