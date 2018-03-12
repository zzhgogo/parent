package com.zzh.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhuhao on 2018/3/5
 */
public class Test {

    public static void main(String[] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("aop02.xml");
        Math2 math = ctx.getBean("Math2", Math2.class);
        int n1 = 100, n2 = 5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);
    }
}
