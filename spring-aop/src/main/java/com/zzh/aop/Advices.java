package com.zzh.aop;

/**
 * Created by zhuhao on 2018/3/5
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 通知类，横切逻辑
 *
 */
public class Advices {

    public void before(JoinPoint jp){
        System.out.println("----------前置通知----------");
        System.out.println(jp.getSignature().getName());
    }

    public void after(JoinPoint jp){
        System.out.println("----------最终通知----------");
    }

    public int around(ProceedingJoinPoint joinpoint) {
        try {
            System.out.println("----------before around----------");
            //joinpoint.proceed(); // 执行被通知的方法
            System.out.println("----------after around----------");

        } catch (Throwable e) {
            System.out.println("----------around----------");
        }
        return 1;
    }
}