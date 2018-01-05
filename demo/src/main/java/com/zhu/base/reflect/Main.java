package com.zhu.base.reflect;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Main {

    public static void main(String[] args) throws Exception {
        FruitInfoUtil.getFruitInfo(Apple.class);
    }

    @Test
    public void t1() throws ClassNotFoundException {
        Class c = Person.class;
        //获取所有的属性?
        Field[] fs = c.getDeclaredFields();

        //定义可变长的字符串，用来存储属性
        StringBuffer sb = new StringBuffer();
        //通过追加的方法，将每个属性拼接到此字符串中
        //最外边的public定义
        sb.append(Modifier.toString(c.getModifiers()) + " class " + c.getSimpleName() +"{\n");
        //里边的每一个属性
        for(Field field:fs){
            sb.append("\t");//空格
            sb.append(Modifier.toString(field.getModifiers())+" ");//获得属性的修饰符，例如public，static等等
            sb.append(field.getType().getSimpleName() + " ");//属性的类型的名字
            sb.append(field.getName()+";\n");//属性的名字+回车
        }

        Method[] mh = c.getDeclaredMethods();

        for(Method method: mh){
            sb.append("\t");
            sb.append(Modifier.toString(method.getModifiers())+" ");
            sb.append(method.getReturnType()+" ");
            sb.append(method.getName() + "{\n");
            sb.append("\t}\n");
        }

        sb.append("}");

        System.out.println(sb);
    }

    @Test
    public void t2() throws Exception {
        Class<?> clazz = Person.class;
//        Method[] methods = clazz.getMethods();
//        for (Method method : methods) {
//            System.out.println(method);
//        }

        Method m1 = clazz.getMethod("setName", String.class);
        Method m2 = clazz.getMethod("getName");
        Object userInfo = clazz.newInstance();
        m1.invoke(userInfo,"qwerqwerqwerqwerqwer");
        System.out.println("调用get方法："+m2.invoke(userInfo));

    }
}
