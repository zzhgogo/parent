package com.zhu.base.selenium;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:zhuhao
 * @Description:
 * @Date:
 **/
public class TingLiSpider {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Site site = Site.me()
            .setTimeOut(1000 * 15)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
            .setRetryTimes(8);

    private static HttpClientDownloader downloader = new HttpClientDownloader();


    public void downLoad(String url){
        Request request0 = new Request();
        request0.setMethod(HttpConstant.Method.GET);
        request0.setUrl(url);
        Page page0 = downloader.download(request0, site.toTask());
        Html html0 = page0.getHtml();

        Selectable selectable = html0.$("#js_content mpvoice");

        Document document = Jsoup.parse(selectable.get());
        Element element = document.getElementsByTag("mpvoice").first();


        String name = element.attr("name");

        String voiceUrl = "https://res.wx.qq.com/voice/getvoice?mediaid="+element.attr("voice_encode_fileid");
        try {
            downFile(name+".mp3", voiceUrl);
            logger.info("下载听力：{} 成功",name+".mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  void downFile(String name, String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        File storeFile = new File("/data/tingli/"+name);
        FileOutputStream output = new FileOutputStream(storeFile);
        output.write(EntityUtils.toByteArray(response.getEntity()));
        output.close();
    }


    public static void main(String[] args) throws IOException {
        List<String> list = FileUtils.readLines(new File("/Users/zhuhao/project/parent/tl"));
        TingLiSpider spider = new TingLiSpider();
        list.stream().filter(m -> m.contains("http")).forEach(url -> {
            try{
                spider.downLoad(url);
            }catch (Exception e){

            }
        });
}


}
