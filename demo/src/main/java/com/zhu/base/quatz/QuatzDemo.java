package com.zhu.base.quatz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.util.concurrent.CountDownLatch;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @Author: zhuhao
 * @Date: 2018/10/23 下午5:28
 * @Description:
 */
public class QuatzDemo {

    private CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void t1() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        Scheduler scheduler = schedulerFactory.getScheduler();

        scheduler.start();

        JobDetail job = newJob(DJob.class)
                .withIdentity("myJob", "group1")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(10))
                .build();


        scheduler.scheduleJob(job, trigger);
        latch.await();
    }
}
