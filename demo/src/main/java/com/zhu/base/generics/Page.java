package com.zhu.base.generics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by as on 2017/9/8.
 */
//泛型用法
public class Page<T extends Man, Pk> {

    T save(T entity){
        return entity;
    }

    T find(Pk pk){

        return null;
    }
}
