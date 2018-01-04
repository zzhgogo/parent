package com.zhu.base.property;

import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Author: as
 * Time:  2017/9/25 10:37
 * Description: Property文件读取设置
 */
public class Mypropetry {

    /**
     * 读取properties文件
     */
    @Test
    public void readPorperty(){
        Properties prop = new Properties();
        try {
            prop.load(Mypropetry.class.getResourceAsStream("/my.properties"));
            System.out.println(prop.getProperty("user.name"));
            System.out.println(prop.getProperty("user.phone"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 写入properties文件
     */
    @Test
    public void writePorperty(){
        Properties prop = new Properties();
        prop.setProperty("name","tinyfun");
        prop.setProperty("age","25");
        prop.setProperty("sex","man");
        prop.setProperty("title","software developer");
        try {
            String filepath = Mypropetry.class.getResource("/test.properties").getFile();
            PrintStream printStream = new PrintStream(filepath);
            System.out.print(filepath);
            prop.list(printStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取操作系统参数
     */
    @Test
    public void testSystemProperty(){
        System.out.println("java_vendor:" + System.getProperty("java.vendor"));
        System.out.println("java_vendor_url:" + System.getProperty("java.vendor.url"));
        System.out.println("java_home:" + System.getProperty("java.home"));
        System.out.println("java_class_version:" + System.getProperty("java.class.version"));
        System.out.println("java_class_path:" + System.getProperty("java.class.path"));
        System.out.println("os_name:" + System.getProperty("os.name"));
        System.out.println("os_arch:" + System.getProperty("os.arch"));
        System.out.println("os_version:" + System.getProperty("os.version"));
        System.out.println("user_name:" + System.getProperty("user.name"));
        System.out.println("user_home:" + System.getProperty("user.home"));
        System.out.println("user_dir:" + System.getProperty("user.dir"));
        System.out.println("java_vm_specification_version:" + System.getProperty("java.vm.specification.version"));
        System.out.println("java_vm_specification_vendor:" + System.getProperty("java.vm.specification.vendor"));
        System.out.println("java_vm_specification_name:" + System.getProperty("java.vm.specification.name"));
        System.out.println("java_vm_version:" + System.getProperty("java.vm.version"));
        System.out.println("java_vm_vendor:" + System.getProperty("java.vm.vendor"));
        System.out.println("java_vm_name:" + System.getProperty("java.vm.name"));
        System.out.println("java_ext_dirs:" + System.getProperty("java.ext.dirs"));
        System.out.println("file_separator:" + System.getProperty("file.separator"));
        System.out.println("path_separator:" + System.getProperty("path.separator"));
        System.out.println("line_separator:" + System.getProperty("line.separator"));
    }
}
