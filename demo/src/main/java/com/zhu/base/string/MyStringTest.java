package com.zhu.base.string;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.zhu.base.property.Mypropetry;
import com.zhu.base.reflect.Person;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;


import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import us.codecraft.webmagic.utils.HttpClientUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;




/**
 * @author zhuhao
 */
public class MyStringTest {


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
    public void t7(){
        System.out.println(new Date());
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
        Cache<String, Object> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(3, TimeUnit.MINUTES)
                .concurrencyLevel(10)
                .recordStats()
                .build();

        cache.put("key", Lists.newArrayList("12312313"));

        Object value = cache.getIfPresent("key");

        System.out.println(value);
    }

    @Test
    public void t11(){


    }



}
