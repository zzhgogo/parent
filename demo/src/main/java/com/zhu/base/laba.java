package com.zhu.base;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhuhao
 * Date: 2018-05-21
 * Time: 上午10:34
 */
public class laba {

    // https://blog.csdn.net/lengguoxing/article/details/78414975
    @Test
    public void t1(){
        /**获取单词，并且去重**/
        List<String> list = Arrays.asList("hello welcome", "world hello", "hello world", "hello world welcome");

        //map和flatmap的区别
        list.stream().map(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("---------- ");
        list.stream().flatMap(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList()).forEach(System.out::println);

        //实际上返回的类似是不同的
        List<Stream<String>> listResult = list.stream().map(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList());
        List<String> listResult2 = list.stream().flatMap(item -> Arrays.stream(item.split(" "))).distinct().collect(Collectors.toList());

        //也可以这样
        list.stream().map(item -> item.split(" ")).flatMap(Arrays::stream).distinct().collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("================================================");
    }

    //http://www.java2s.com/Tutorials/Java/Java_Stream/0290__Java_Stream_Collect_to_Map.htm

    //https://www.cnblogs.com/java-zhao/p/5492122.html
    @Test
    public void t2() {

        List<Person> list = new ArrayList();
        list.add(new Person(1, "haha"));
        list.add(new Person(2, "rere"));
        list.add(new Person(3, "fefe"));

        Map<Integer, Person> mapp = list.stream().collect(Collectors.toMap(Person::getId, Function.identity()));

        System.out.println(mapp);

        System.out.println(mapp.get(1).getName());

        Map<Integer, String> map = list.stream().collect(Collectors.toMap(Person::getId, Person::getName));

        System.out.println(map);

    }

    class Person {
        private int id;
        private String name;


        Person(int id, String name){
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
