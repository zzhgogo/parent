package com.zhu.base.http;


import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpDemo {


    @Test
    public void t1() {
       Map<String, String> map = new HashMap<>();
       map.put("type", "1");
       map.put("name", "朱昊");
       map.put("mobile", "18244975178");
       for (int i = 0 ; i < 10000; i++){
           String res = formPost("http://test.m.toutiao.manqian.cn/async/addConsultAsk", map);
           System.out.println(res);
       }
    }

    public static String formPost(String url, Map<String, String> map){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<>();
        for(Map.Entry<String, String> entry : map.entrySet()){
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(pairs, "utf-8");
            request.setEntity(formEntity);
            CloseableHttpResponse response = httpClient.execute(request);
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void  downLoadFile(String urlStr, String fileName, String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        byte[] data = bos.toByteArray();
        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }
        System.out.println("info:"+url+" download success");
    }


    public static void  downFile() throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://toutiao.manqian.cn/complaints_guide.pdf");
        CloseableHttpResponse response = httpClient.execute(request);
        File storeFile = new File("/data/complaints_guide.pdf");
        FileOutputStream output = new FileOutputStream(storeFile);
        output.write(EntityUtils.toByteArray(response.getEntity()));
        output.close();
    }


    public static void main(String[] args) throws IOException{
       downFile();
    }


}
