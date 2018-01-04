package com.zhu.base.string;

import com.zhu.base.property.Mypropetry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA.
 * Author: as
 * Time:  2017/9/28 15:31
 * Description:
 */
public class MyStringTest {

    @Test
    public void IOU() throws Exception{
        List<String> lines = new ArrayList<>();
        for (int i = 0 ; i < 100000 ; i++){
            lines.add("非凡之星-"+i);
        }
        IOUtils.writeLines(lines,  null, new FileOutputStream("E://1.txt"));
        FileInputStream fileInputStream = new FileInputStream("E://1.txt");
        List<String> list = IOUtils.readLines(fileInputStream);

        FileUtils.writeLines(new File("E://1.txt"), lines);
        System.out.print(list);

    }
    @Test
    public void STR() throws Exception{
        
//        System.out.println(StringUtils.isBlank(""));
//        System.out.println(StringUtils.isBlank(null));
//
//        System.out.println(StringUtils.isEmpty(""));
//        System.out.println(StringUtils.isEmpty(null));

//        System.out.println(StringUtils.trimToEmpty(null));
//        System.out.println(StringUtils.trimToNull(""));

        List<String> lines = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++){
            lines.add("非凡之星");
        }
        System.out.println(StringUtils.join(lines, ""));
        System.out.println(StringUtils.remove("asdf", "sd"));



    }
}
