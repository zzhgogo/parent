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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UcloudOss {

    private static String url  = "http://manqian.ufile.ucloud.cn/?list&prefix=%s&marker=%s&limit=%s";

    public static void main(String[] args) throws Exception{
       String urll = String.format(url,  ".ufile.ucloud.cn", "", "10");
       System.out.println(getFileList(urll));
    }
    public  static String getFileList(String url) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        request.setHeader("prefix", ".ufile.ucloud.cn");
        request.setHeader("marker", "");
        request.setHeader("limit", "10");
        request.setHeader("Authorization", "");
        CloseableHttpResponse response = httpClient.execute(request);
        String result = EntityUtils.toString(response.getEntity());
        return result;
    }
}
