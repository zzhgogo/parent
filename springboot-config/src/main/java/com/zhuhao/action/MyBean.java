package com.zhuhao.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @Author:zhuhao
 * @Description:
 * @Date:
 **/
//@Component
public class MyBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void setBeanName(String s) {
        logger.info(s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        logger.info(beanFactory.containsBean("myBean")+"");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info(applicationContext.containsBean("myBean")+"");
    }


    public void init(){

    }

}
