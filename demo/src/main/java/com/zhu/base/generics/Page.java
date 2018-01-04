package com.zhu.base.generics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by as on 2017/9/8.
 */
//泛型用法
public class Page<T> implements Serializable{
    private List<T> list = new LinkedList<>();

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList(){
        return this.list;
    }

    public T getItemByIndex(Integer i){
        if (i < list.size())
            return list.get(i);
        else
            return null;
    }
}
