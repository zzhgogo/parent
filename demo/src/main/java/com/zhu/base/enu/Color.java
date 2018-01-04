package com.zhu.base.enu;

/**
 * JAVA 枚举类
 * Created by as on 2017/8/22.
 */
public enum  Color {

    RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLOW("黄色", 4);

    private String name ;
    private int index ;

    Color( String name , int index ){
        this.name = name ;
        this.index = index ;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public static void main(String[] args) {

        //输出某一枚举的值
        System.out.print( Color.RED.getName()+" ");
        System.out.println( Color.RED.getIndex());

        //遍历所有的枚举
        for(Color color : Color.values()){
            System.out.println( color + "  name: " + color.getName() + "  index: " + color.getIndex() );
        }
    }
}
