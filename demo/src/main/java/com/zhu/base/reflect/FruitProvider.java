package com.zhu.base.reflect;

import java.lang.annotation.*;

/**
 * 水果供应者注解
 *
 * @author peida
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitProvider {

    String address() default "湖北";

    int id() default -1;

    String name() default "非凡之星";


}