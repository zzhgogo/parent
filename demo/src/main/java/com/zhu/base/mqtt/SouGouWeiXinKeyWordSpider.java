package com.zhu.base.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class SouGouWeiXinKeyWordSpider {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Site site = Site.me()
            .setTimeOut(1000 * 15)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
            .setRetryTimes(8);

    private static HttpClientDownloader downloader = new HttpClientDownloader();

    private final static String keyword_and_time_search_url = "http://weixin.sogou.com/weixin?usip=&query={KEYWORD}&ft={STARTTIME}&tsn=5&et={ENDTIME}&interation=&type=2&wxid=&page={PAGE}&ie=utf8";


    public List<String> searchKeyWord(String keyWord) {
        List<String> links = new ArrayList<>();
        Request request0 = new Request();
        request0.setMethod(HttpConstant.Method.GET);
        request0.setUrl("https://weixin.sogou.com/weixin?usip=&query=%E8%B4%A2%E5%AF%8C&ft=2019-03-21&tsn=5&et=2019-03-27&interation=&type=2&wxid=&page=3&ie=utf8");
        site.addHeader("Referer", "https://weixin.sogou.com/weixin?usip=&query=%E8%B4%A2%E5%AF%8C&ft=2019-03-21&tsn=5&et=2019-03-27&interation=&type=2&wxid=&page=3&ie=utf8");
        Page page0 = downloader.download(request0, site.toTask());
        Html html0 = page0.getHtml();
        logger.info(html0.toString());
        return links;
    }

    /**
     * 关键词+时间段查询
     *
     * @param keyWord
     * @param startTime 2018-05-01
     * @param endTime   2018-05-02
     * @return
     */
    public List<String> searchKeyWord(String keyWord, String startTime, String endTime, int page) {
        List<String> links = new ArrayList<>();

        String url = keyword_and_time_search_url
                .replace("{KEYWORD}", keyWord).replace("{STARTTIME}", startTime)
                .replace("{ENDTIME}", endTime).replace("{PAGE}", String.valueOf(page));
        Request request0 = new Request();
        request0.setMethod(HttpConstant.Method.GET);
        request0.setUrl(url);

        site.addHeader("Referer", url);

        Page page0 = downloader.download(request0, site.toTask());
        Html html0 = page0.getHtml();


        List<Selectable> list0 = html0.css("#main > div.news-box > ul > li").nodes();
        list0.stream().forEach( m -> {

            // 原始链接
            String originUrl = m.css("div.txt-box > h3 > a", "data-share").get();
            //标题
            Html html = new Html(m.css("div.txt-box > h3 > a").get());
            String title = html.getDocument().text().replace(" ", "");
            //logger.info("文章：标题[{}], URL[{}]", title,originUrl);

            links.add(originUrl);
        });
        logger.info(html0.toString());
        return links;
    }


    public void crawlDetail(String url) {
        Request request0 = new Request();
        request0.setMethod(HttpConstant.Method.GET);
        request0.setUrl(url);
        site.addHeader("Referer","https://weixin.sogou.com/");
        Page page0 = downloader.download(request0, site.toTask());
        logger.info(page0.getRawText());
    }


    public static SouGouWeiXinKeyWordSpider getInstance() {
        return new SouGouWeiXinKeyWordSpider();
    }

    public static void main(String[] args) {
        SouGouWeiXinKeyWordSpider spider = SouGouWeiXinKeyWordSpider.getInstance();
        List<String> links = spider.searchKeyWord("新三板", "2019-03-20", "2019-03-27", 1);

//        MpWeiXinArticleSpider articleSpider = new MpWeiXinArticleSpider();
//
//        links.stream().limit(1).map(articleSpider::crawlDetail).forEach(o ->{
//            System.out.println(JSON.toJSONString(o));
//        });


    }
}
