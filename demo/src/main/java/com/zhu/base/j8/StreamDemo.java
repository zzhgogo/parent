package com.zhu.base.j8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * @author zhuhao
 * @title: StreamDemo
 * @projectName parent
 * @description: TODO
 * @date 2019/4/252:46 PM
 */
// https://www.cnblogs.com/andywithu/p/7404101.html
public class StreamDemo {

    @Test
    public void t1() {
        Stream<String> stream = Stream.generate(() -> "user").limit(20);
        stream.forEach(System.out::println);
    }

    //创建一个空的stream
    @Test
    public void t2() {
        Stream<Integer> stream = Stream.empty();
    }

    //创建无限流，通过limit提取指定大小
    @Test
    public void t3() {
        Random random = new Random();
        Stream.generate(() -> random.nextInt(100)).limit(100).forEach(System.out::println);
    }

    //产生规律的数据
    @Test
    public void t4() {
        Stream.iterate(0, x -> x + 1).limit(10).forEach(System.out::println);
//        Stream.iterate(0, x -> x).limit(10).forEach(System.out::println);
//        Stream.iterate(0, UnaryOperator.identity()).limit(10).forEach(System.out::println);
    }

    /**
     * flapMap：拆解流
     */
    @Test
    public void t5() {
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"e", "f", "c", "d"};
        String[] arr3 = {"h", "j", "c", "d"};
        // Stream.of(arr1, arr2, arr3).flatMap(x -> Arrays.stream(x)).forEach(System.out::println);
        Stream.of(arr1, arr2, arr3).flatMap(Arrays::stream).forEach(System.out::println);
    }

    /**
     * 排序
     */
    @Test
    public void t6() {
        Random random = new Random();
        Stream.generate(() -> random.nextInt(100)).limit(100).sorted(Comparator.comparing(Integer::intValue)).forEach(System.out::println);
        Stream.generate(() -> random.nextInt(100)).limit(100).sorted(Comparator.comparing(Integer::intValue).reversed()).forEach(System.out::println);
    }

    /**
     * 合并
     */
    @Test
    public void t7() {
        Random random = new Random();
        Stream<Integer> stream1 = Stream.generate(() -> random.nextInt(10)).limit(100);
        Stream<Integer> stream2 = Stream.generate(() -> random.nextInt(10)).limit(100);
        Stream.concat(stream1, stream2).distinct().forEach(System.out::println);
    }


}
