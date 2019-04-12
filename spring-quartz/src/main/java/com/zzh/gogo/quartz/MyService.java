package com.zzh.gogo.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;


/**
 * @Author: zhuhao
 * @Date: 2018/10/26 上午10:55
 * @Description:
 */
@Service("myService")
public class MyService implements InitializingBean{


    /**
     * 日志记录对象
     */
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void method1(){
        log.info("method1 start ...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("create MyService");
    }
}
