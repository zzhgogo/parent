package com.zhu.base.string;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author:zhuhao
 * @Description:
 * @Date:
 **/
public class SilianTest {

    @Test
    public void t1() throws Exception{
        Set<String> result = new HashSet<>();
        InputStream inputStream1  = new FileInputStream("/Users/zhuhao/Documents/p_silan.txt");
        List<String> logs1 = IOUtils.readLines(inputStream1);
        for (String log : logs1){
            log = log.substring(log.indexOf("http"));
            result.add(log.trim());
        }

        InputStream inputStream2  = new FileInputStream("/Users/zhuhao/Documents/p_silan1.txt");
        List<String> logs2 = IOUtils.readLines(inputStream2);
        for (String log : logs2){
            log = log.substring(log.indexOf("http"));
            result.add(log.trim());
        }

        FileWriter fileWriter = new FileWriter("/data/p_silian_1204.txt");
        IOUtils.writeLines(result, IOUtils.LINE_SEPARATOR, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    @Test
    public void t2() throws Exception{
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.select(15);
        FileReader fileReader = new FileReader("/data/p_silian_1204.txt");
        List<String> list1 = IOUtils.readLines(fileReader);
        list1 = list1.stream().filter(m -> m.startsWith("http://toutiao.manqian.cn")).collect(Collectors.toList());
        fileReader.close();
        String[] arr = new String[list1.size()];
        list1.toArray(arr);
        jedis.sadd("p_silian", arr);
        jedis.close();
    }

    @Test
    public void t3() throws Exception{
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.select(15);
        FileReader fileReader = new FileReader("/data/m_silian_1204.txt");
        List<String> list1 = IOUtils.readLines(fileReader);
        list1 = list1.stream().map(m -> m.replace("m.toutiao.manqian.cn", "toutiao.manqian.cn")).collect(Collectors.toList());
        fileReader.close();
        String[] arr = new String[list1.size()];
        list1.toArray(arr);
        jedis.sadd("p_silian", arr);
        jedis.close();
    }

    @Test
    public void t4() throws Exception{
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.select(15);
        Set<String> set = jedis.smembers("p_silian");
        FileWriter fileWriter = new FileWriter("/data/p_silian_1204.txt");
        IOUtils.writeLines(set, IOUtils.LINE_SEPARATOR, fileWriter);
        fileWriter.flush();
        fileWriter.close();

        PrintWriter printWriter = new PrintWriter("/data/m_silian_1204.txt");
        for (String url: set){
            url = url.replace("http://toutiao.manqian.cn", "http://m.toutiao.manqian.cn");
            printWriter.println(url);
        }
        printWriter.flush();
        printWriter.close();

    }


}
