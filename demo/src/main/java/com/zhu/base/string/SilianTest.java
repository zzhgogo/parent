package com.zhu.base.string;

import com.google.common.collect.Lists;
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
        FileReader fileReader = new FileReader("/data/m_silian_1214.txt");
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
        Jedis jedis = new Jedis("127.0.0.1",9379);
        jedis.select(11);
        System.out.println(jedis.scard("p_silan"));
        List<String> list = Lists.newArrayList();

        long total = jedis.scard("p_silan");
        long pagesize =20l;
        long pages = total / pagesize;
        for (int i = 0; i < total/pagesize; i++){
            Set<String> set = jedis.spop("p_silan" , pagesize);
            list.addAll(set);
            System.out.println(i +", "+pages);
        }
        FileWriter fileWriter = new FileWriter("/data/p_silian_0131.txt");
        IOUtils.writeLines(list, IOUtils.LINE_SEPARATOR, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    @Test
    public void t5() throws Exception{
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.select(15);
        FileReader fileReader = new FileReader("/data/p_silian_1214.txt");
        List<String> list1 = IOUtils.readLines(fileReader);
        fileReader.close();
        for(String url : list1){
            jedis.sadd("p_silian", url);
        }
    }

    @Test
    public void t6() throws Exception{
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.select(15);
        Set<String> strings = jedis.smembers("p_silian");
        for (String string: strings){
            jedis.sadd("m_silian", string.replace("\"", ""));
        }
    }

    @Test
    public void t7() throws Exception{
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.select(15);
        Set<String> strings = jedis.smembers("m_silian");
        strings = strings.stream().map(m -> m.replace("http://toutiao.manqian.cn", "http://m.toutiao.manqian.cn")).collect(Collectors.toSet());
        FileWriter fileWriter = new FileWriter("/data/m_silian_1204.txt");
        IOUtils.writeLines(strings, IOUtils.LINE_SEPARATOR, fileWriter);
        fileWriter.flush();
        fileWriter.close();

    }


    @Test
    public void t8() throws Exception{
        FileReader fileReader = new FileReader("/data/p_silian_0131.txt");
        List<String> list1 = IOUtils.readLines(fileReader);
        fileReader.close();
        int count = list1.size();

        for(int i = 0; i < count/95000+1; i++){
            int start = i * 95000;
            int end   = i * 95000 + 95000;
            end = end < count ? end : count;
            List<String> subList = list1.subList(start, end);
            FileWriter fileWriter = new FileWriter("/data/p_silian_0131_"+i+".txt");
            subList.stream().forEach(System.out::println);
            IOUtils.writeLines(subList, null, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }


    }




}
