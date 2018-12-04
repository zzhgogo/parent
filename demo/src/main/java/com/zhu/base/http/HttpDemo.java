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
import java.net.URLConnection;
import java.util.*;

public class HttpDemo {


    public static String formPost(String url, Map<String, String> map) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
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

    public static void downLoadFile(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        byte[] data = bos.toByteArray();
        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");
    }

    public static void downFile() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://toutiao.manqian.cn/complaints_guide.pdf");
        CloseableHttpResponse response = httpClient.execute(request);
        File storeFile = new File("/data/complaints_guide.pdf");
        FileOutputStream output = new FileOutputStream(storeFile);
        output.write(EntityUtils.toByteArray(response.getEntity()));
        output.close();
    }


    public static void main(String[] args) throws FileNotFoundException {
        Map<String,String> headers=new HashMap();
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Cache-Control", "no-cache");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36");
        downLoadFile("http://wx.qlogo.cn/mmhead/Q3auHgzwzM6m0icBwkbMY9bSuDlGMvw9m39sTMHp0xZE3Ku36WWBn2Q/10", "/data/test.jpg",headers);
    }

    /**
     * 下载文件
     *
     * @param fileurl 要下载的文件的网络路径
     * @param path    文件存放的位置
     * @param headers 下载文件的请求头
     * @throws FileNotFoundException
     */
    public static void downLoadFile(String fileurl, String path, Map<String, String> headers) throws FileNotFoundException {
        File saveFile = new File(path);
        if (saveFile.isDirectory()) {
            throw new FileNotFoundException("传入的文件路径不能是文件夹");
        }
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        InputStream in = null;
        OutputStream os = null;
        URLConnection conn = null;
        try {
            URL url = new URL(fileurl);
            conn = url.openConnection();
            Set<String> headerskey = headers.keySet();
            for (String headerkey : headerskey) {
                conn.setRequestProperty(headerkey, headers.get(headerkey));
            }
            conn.setUseCaches(false);
            in = conn.getInputStream();
            os = new FileOutputStream(saveFile);
            bis = new BufferedInputStream(in, 1024 * 1024);//设置读入缓存为1M
            bos = new BufferedOutputStream(os, 1024 * 1024);//设置写缓存为1M
            byte[] b = new byte[1024 * 1024];
            int i = 0;
            while ((i = bis.read(b)) != -1) {
                bos.write(b, 0, i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void t1() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");
        map.put("name", "朱昊");
        map.put("mobile", "18244975178");
        for (int i = 0; i < 10000; i++) {
            String res = formPost("http://test.m.toutiao.manqian.cn/async/addConsultAsk", map);
            System.out.println(res);
        }
    }


}
