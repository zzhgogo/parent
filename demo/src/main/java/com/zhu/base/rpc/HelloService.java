package com.zhu.base.rpc;

/**
 * @Author: zhuhao
 * @Date: 2018/10/29 11:51 AM
 * @Description:
 */
public class HelloService implements IHello {

    @Override
    public String sayHello(String info) {
        String result = "hello : " + info;
        System.out.println(result);
        return result;
    }
}
