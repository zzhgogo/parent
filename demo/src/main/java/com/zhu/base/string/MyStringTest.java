package com.zhu.base.string;

import com.zhu.base.property.Mypropetry;
import com.zhu.base.reflect.Person;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created with IntelliJ IDEA.
 * Author: as
 * Time:  2017/9/28 15:31
 * Description:
 */
@SuppressWarnings("ALL")
public class MyStringTest {


    @Test
    public void iuo() throws Exception{
        List<String> lines = new ArrayList<>();
        for (int i = 0 ; i < 100000 ; i++){
            lines.add("非凡之星-"+i);
        }
        String path = MyStringTest.class.getResource("/").getPath()+"1.txt";
        File file = new File(path);
        if(file.exists()==false){
            file.createNewFile();
        }
        IOUtils.writeLines(lines,  null, new FileOutputStream(path));
        FileInputStream fileInputStream = new FileInputStream(path);
        List<String> list = IOUtils.readLines(fileInputStream);
        FileUtils.writeLines(file, lines);

        System.out.print(list);

    }
    @Test
    public void str() throws Exception{
        
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

    @Test
    public void t4(){
        Long start1 = System.nanoTime();
        String a = "abc";
        a = a +"efg";
        Long end1 = System.nanoTime();
        System.out.println("time: "+(end1-start1));

        Long start2 = System.nanoTime();
        StringBuffer aa = new StringBuffer("abc");
        aa.append("efg");
        Long end2 = System.nanoTime();
        System.out.println("time: "+(end2-start2));

        Long start3 = System.nanoTime();
        StringBuilder aaa = new StringBuilder("abc");
        aaa.append("efg");
        aaa.toString();
        Long end3 = System.nanoTime();
        System.out.println("time: "+(end3-start3));

    }

    @Test
    public void t5(){
        String s = MyStringTest.class.getResource("/").getPath();
        System.out.println(s);
    }

    @Test
    public void md5(){
        //待加密字符串
        String str  = "abcdefghi";

        //1、MD5加密
        String md5Str = DigestUtils.md5Hex(str);
        System.out.println("MD5-->" + md5Str);

        //SHA1加密
        String sha1Str = DigestUtils.sha1Hex(str);
        System.out.println("SHA1-->" + sha1Str);

        //Base64加密
        String base64Str = Base64.encodeBase64String(str.getBytes());
        System.out.println("base64加密-->" + base64Str);

        //Base64解密
        String base64DecodeStr = new String(Base64.decodeBase64(base64Str));
        System.out.println("base64解密-->" + base64DecodeStr);

    }

    @Test
    public void math(){
        System.out.println(Math.ceil(1.2));
        System.out.println(Math.round(1.2));
        System.out.println(Math.floor(1.9));
    }

    @Test
    public void t6(){
        String str  = "aaaccd";
        char[] arr = str.toCharArray();
        char temp = ' ';
        int count = 1;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0 ; i < arr.length ; i++){
            if(i==0){
                temp = arr[0];
            }else {
                if(temp==arr[i]){
                    count++;
                }else {
                    stringBuffer.append(temp+""+count);
                    temp = arr[i];
                    count = 1;
                }
            }
            if(arr.length==1){
                stringBuffer.append(temp+""+count);
            } else if(arr.length==i+1){
                stringBuffer.append(temp+""+count);
            }

        }
        System.out.println(stringBuffer);
    }

    @Test
    public void t7(){
        System.out.println( new Person() instanceof Object);
        Map<Person ,Person> hMap = new HashMap<>();
    }

    @Test
    public void t8(){
        int year = 2018;
        int week = 18;
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.WEEK_OF_YEAR, 26);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss ");
        String time = formatter.format(c.getTime());
        System.out.println(time);
    }

    @Test
    public void t9(){
        String s = ";:00594510=2609013500152=5801?";
        s = s.replaceAll(";|:|=|\\?",",");
        System.out.println(s);
        String[] arr = s.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(arr));
        //list.stream().forEach(System.out::println);
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            if(StringUtils.isBlank(iterator.next())){
                iterator.remove();
            }
        }

        list.stream().forEach(System.out::println);
    }

    @Test
    public void t10(){
        String introduce = "CFP认证理财师1";
        introduce = introduce + ",";
        introduce = introduce.replaceAll(",|，|、|\\?",",");
        System.out.println(introduce.substring(0, introduce.indexOf(",")));
    }

    @Test
    public void t11(){
        long a = System.currentTimeMillis();
        System.out.println(a/400);
    }

    @Test
    public void t12(){
        Pattern p = Pattern.compile("^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$",Pattern.CASE_INSENSITIVE );
        Matcher m = p.matcher("http://www.qqgb.com/Program/Java/JavaFAQ/JavaJ2SE/Program_146959.html");
        if(m.find()){
            System.out.println(m.group());
        }

        m = p.matcher("http://baike.baidu.com/view/230199.htm?fr=ala0_1");
        if(m.find()){
            System.out.println(m.group());
        }
        m = p.matcher("http://www.google.cn/gwt/x?u=http%3A%2F%2Fanotherbug.blog.chinajavaworld.com%2Fentry%2F4550%2F0%2F&btnGo=Go&source=wax&ie=UTF-8&oe=UTF-8");
        if(m.find()){
            System.out.println(m.group());
        }
        m = p.matcher("http://zh.wikipedia.org:80/wiki/Special:Search?search=tielu&go=Go");
        if(m.find()){
            System.out.println(m.group());
        }
    }

    @Test
    public void t13(){
        isConnect("http://mmbiz.qpic.cn/mmbiz_jpg/6sVib6Gqze40rMEtoAVyKUJvgIMAUHjxZrQPy7ETnwgNcU0PQ5TgZscZGGHvZP5oqUPibCc8PCa0uXXoiaicpyTsjQ/0");
    }
    /**
     * 功能：检测当前URL是否可连接或是否有效,
     * 描述：最多连接网络 3 次, 如果 3 次都不成功，视为该地址不可用
     * @param  urlStr 指定URL网络地址
     * @return URL
     */
    public synchronized String isConnect(String urlStr) {
        int counts = 0;
        String retu = "";
        if (urlStr == null || urlStr.length() <= 0) {
            return null;
        }
        while (counts < 3) {
            long start = 0;
            try {
                URL url = new URL(urlStr);
                start = System.currentTimeMillis();
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                int state = con.getResponseCode();
                 System.out.println("请求断开的URL一次需要："+(System.currentTimeMillis()-start)+"毫秒");
                if (state == 200) {
                    retu = "ok";
                    System.out.println(urlStr+"--可用");
                }
                break;
            }catch (Exception ex) {
                counts++;
                System.out.println("请求断开的URL一次需要："+(System.currentTimeMillis()-start)+"毫秒");
                System.out.println("连接第 "+counts+" 次，"+urlStr+"--不可用");
                continue;
            }
        }
        return retu;
    }




}
