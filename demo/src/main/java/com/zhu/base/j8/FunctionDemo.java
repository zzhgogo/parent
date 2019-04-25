package com.zhu.base.j8;

import org.junit.Test;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author zhuhao
 * @title: FunctionDemo
 * @projectName parent
 * @description: TODO
 * @date 2019/4/253:20 PM
 */
// https://www.cnblogs.com/linzhanfly/p/9686941.html

public class FunctionDemo {

    class MyFuncton implements Function<Integer, Integer> {
        @Override
        public Integer apply(Integer s) {
            return s * s;
        }
    }

    class MyPredicate implements Predicate<Integer> {
        @Override
        public boolean test(Integer integer) {
            return integer.intValue() % 2 == 0;
        }
    }

    class MyConsumer implements Consumer<Integer> {

        @Override
        public void accept(Integer integer) {
            System.out.println(integer);
        }
    }

    class MySupplier implements Supplier<Integer> {
        @Override
        public Integer get() {
            Random random = new Random();
            return random.nextInt(100);
        }
    }

    @Test
    public void t1() {
        MyFuncton myFuncton = new MyFuncton();
        MyPredicate myPredicate = new MyPredicate();
        MyConsumer myConsumer = new MyConsumer();
        MySupplier mySupplier = new MySupplier();
        Stream.generate(mySupplier).limit(10).map(myFuncton).filter(myPredicate).forEach(myConsumer);
    }


}
