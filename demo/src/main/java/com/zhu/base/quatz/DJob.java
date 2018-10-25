package com.zhu.base.quatz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: zhuhao
 * @Date: 2018/10/23 下午5:47
 * @Description:
 */
public class DJob implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(jobExecutionContext.getFireTime());
    }
}
