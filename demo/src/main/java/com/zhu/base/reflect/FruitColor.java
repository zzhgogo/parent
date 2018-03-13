package com.zhu.base.reflect;

import java.lang.annotation.*;

/**
 * Created by as on 2017/8/18.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {

    /**
     * 颜色枚举
     * @author peida
     *
     */
    enum Color{ BULE, RED, GREEN}

    /**
     * 颜色属性
     * @return
     */
    Color fruitColor() default Color.GREEN;

}
