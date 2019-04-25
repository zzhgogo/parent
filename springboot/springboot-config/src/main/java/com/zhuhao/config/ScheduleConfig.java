package com.zhuhao.config;

import com.zhuhao.schedule.TestJob;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @Author: zhuhao
 * @Date: 2018/10/24 下午5:00
 * @Description:
 */
//@Configuration
public class ScheduleConfig implements ApplicationContextAware {

    private final static Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }



    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws Exception {

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        //job1
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setName("job1");
        jobDetailFactoryBean.setTargetBeanName("testJob");
        jobDetailFactoryBean.setTargetMethod("job1");
        jobDetailFactoryBean.setBeanFactory(applicationContext);
        jobDetailFactoryBean.afterPropertiesSet();

        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("job1");
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        cronTriggerFactoryBean.setCronExpression("*/1 * * * * ?");

        cronTriggerFactoryBean.afterPropertiesSet();

        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setName("job1");
        simpleTriggerFactoryBean.setRepeatInterval(1000);
        simpleTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        simpleTriggerFactoryBean.afterPropertiesSet();



        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean.getObject());



        //schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean.getObject());


        //schedulerFactoryBean.getScheduler().start();
        schedulerFactoryBean.afterPropertiesSet();

        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler stdScheduler () throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        return scheduler;
    }


}
