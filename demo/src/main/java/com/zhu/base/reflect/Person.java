package com.zhu.base.reflect;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by as on 2017/8/18.
 */
public class Person implements Serializable {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) throws Exception{
        Person person = new Person("luoxn28", 23);
        Class clazz = person.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            PropertyDescriptor descriptor = new PropertyDescriptor(key, clazz);
            Method method = descriptor.getReadMethod();
            Object value = method.invoke(person);
            System.out.println(key + ":" + value);

        }
    }

    public void method(){
        System.out.println("123123");
    }

}