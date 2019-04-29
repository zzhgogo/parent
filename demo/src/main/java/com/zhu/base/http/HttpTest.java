package com.zhu.base.http;

import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

public class HttpTest {


    @Test
    public void t1() throws IOException {
        String url = "https://www.manqian.cn/";
        HttpResponse response = HttpClientUtil.doGet(url);
        String res = HttpClientUtil.responseToString(response);
        System.out.println(res);
    }
}
