package com.zhu.base.reflect;

import java.lang.annotation.*;

/**
 * Created by as on 2017/8/18.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {

    public String value() default "非凡之星___";
}
