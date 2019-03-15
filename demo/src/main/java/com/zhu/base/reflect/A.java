package com.zhu.base.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:zhuhao
 * @Description:
 * @Date:
 **/
public class A<T> {


    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public A() {
        Type type = getClass();
        System.out.println("getClass() == " + getClass());
        System.out.println("type = " + type);
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        System.out.println("trueType1 = " + trueType);
        trueType = ((ParameterizedType) type).getActualTypeArguments()[1];
        System.out.println("trueType2 = " + trueType);


    }

    public static void main(String[] args) throws Exception {
        A a = new A<Apple>();

        List list = new ArrayList<Apple>();
        System.out.println(a.entityClass);
    }
}
