package com.zhu.base.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author zhuhao
 * @title: HttpTool
 * @projectName parent
 * @description: TODO
 * @date 2019/4/284:33 PM
 */
public class HttpTool {


    /**
     * get请求
     *
     * @return
     */
    public static String doGet(String url) {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                return strResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getConent(String url, String content) {
        HttpClient httpClient = wrapClient(url);
        HttpPost request = new HttpPost(url);

        if (StringUtils.isNotBlank(content)) {
            HttpEntity entity = new StringEntity(content, Charset.defaultCharset());
            request.setEntity(entity);
            try {
                HttpResponse httpResponse = httpClient.execute(request);
                HttpEntity httpEntity = response(httpResponse);
                return EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static HttpEntity response(HttpResponse httpResponse) {
        StatusLine statusLine = httpResponse.getStatusLine();
        if (statusLine == null) {
            return null;
        }
        if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
            return null;
        }
        return httpResponse.getEntity();
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = HttpClients.createDefault();
        return httpClient;
    }

}
