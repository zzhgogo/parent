package com.zhu.base.reflect;

/**
 * Created by as on 2017/8/18.
 */
public class Apple {

    //@FruitName("apple")
    private String appleName;

    @FruitColor(fruitColor = FruitColor.Color.GREEN)
    private String appleColor;

    @FruitProvider(id=1,name="陕西红富士集团",address="陕西省西安市延安路89号红富士大厦")
    private String appleProvider;

    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }
    public String getAppleColor() {
        return appleColor;
    }

    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }
    public String getAppleName() {
        return appleName;
    }
    @Action(value = "西瓜AAAAAAAAAAAAAA")
    public void displayName(String name){
        //this.appleName = name;
        System.out.println(name);
    }

    public String getAppleProvider() {
        return appleProvider;
    }

    public void setAppleProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }

    public static void main(String[] args){
        Apple apple = new Apple();
        System.out.println(apple.getAppleName());
    }
}
