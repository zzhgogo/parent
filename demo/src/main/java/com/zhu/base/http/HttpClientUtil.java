package com.zhu.base.http;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author zhuhao
 * @title: HttpClientUtil
 * @projectName parent
 * @description: TODO
 * @date 2019/4/2911:03 AM
 */
public class HttpClientUtil {


    /**
     * get请求
     *
     * @return
     */
    public static HttpResponse doGet(String url) throws IOException {
        return doGet(url, null, null);
    }

    /**
     * get
     *
     * @param url
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doGet(String url, Map<String, String> headers, Map<String, String> query) throws IOException {
        HttpClient httpClient = wrapClient(url);
        HttpGet request = new HttpGet(buildUrl(url, query));
        addHeader(request, headers);
        return httpClient.execute(request);
    }

    /**
     * post 模拟表单(form)
     *
     * @param url
     * @param headers
     * @param query
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String url,
                                      Map<String, String> headers,
                                      Map<String, String> query,
                                      Map<String, String> body)
            throws Exception {
        HttpClient httpClient = wrapClient(url);
        HttpPost request = new HttpPost(buildUrl(url, query));
        addHeader(request, headers);
        if (body != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for (String key : body.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, body.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }
        return httpClient.execute(request);
    }

    public static HttpResponse doPost(String url, Map<String, String> body) throws Exception {
        return doPost(url, null, null, body);
    }

    /**
     * Post String (application/json)
     *
     * @param url
     * @param headers
     * @param query
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String url, Map<String, String> headers,
                                      Map<String, String> query,
                                      String body)
            throws Exception {
        HttpClient httpClient = wrapClient(url);
        HttpPost request = new HttpPost(buildUrl(url, query));
        addHeader(request, headers);
        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }
        return httpClient.execute(request);
    }

    /**
     * Post stream
     *
     * @param url
     * @param headers
     * @param query
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String url, Map<String, String> headers, Map<String, String> query, byte[] body)
            throws Exception {
        HttpClient httpClient = wrapClient(url);
        HttpPost request = new HttpPost(buildUrl(url, query));
        addHeader(request, headers);
        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }
        return httpClient.execute(request);
    }

    /**
     * Post stream
     *
     * @param url
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String url, byte[] body)
            throws Exception {
        return doPost(url, null, null, body);
    }


    public static String responseToString(HttpResponse response) {
        if (response == null) {
            return null;
        }
        try {
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] responseToByte(HttpResponse response) {
        if (response == null) {
            return new byte[0];
        }
        try {
            return EntityUtils.toByteArray(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * 生成url
     *
     * @param url
     * @param query
     * @return
     */
    private static String buildUrl(String url, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder(url);
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }
        return sbUrl.toString();
    }


    /**
     * 添加请求头
     *
     * @param request
     * @param headers
     */
    private static void addHeader(HttpRequestBase request, Map<String, String> headers) {
        if (headers == null) {
            return;
        }
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
    }

    private static HttpClient wrapClient(String url) {
        if (url.startsWith("https//")) {
            return sslClient();
        }

        return HttpClients.createDefault();
    }

    private static HttpClient sslClient() {
        try {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[]{trustManager}, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            // 创建Registry
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", socketFactory).build();
            // 创建ConnectionManager，添加Connection配置信息
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig).build();
            return closeableHttpClient;
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

}
