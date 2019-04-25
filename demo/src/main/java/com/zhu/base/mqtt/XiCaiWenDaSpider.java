package com.zhu.base.mqtt;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.google.common.collect.Lists;
import com.manqian.toutiao.answer.api.IAnswerService;
import com.manqian.toutiao.answer.api.IQuestionService;
import com.manqian.toutiao.answer.api.IQuestionTagService;
import com.manqian.toutiao.entity.Answer;
import com.manqian.toutiao.entity.Question;
import com.zhu.base.dubbo.ToutiaoDubbo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:zhuhao
 * @Description: 搜狗微信文章采集
 * @Date:
 **/
public class XiCaiWenDaSpider extends ToutiaoDubbo {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Site site = Site.me()
            .setTimeOut(1000 * 15)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
            .setRetryTimes(8);

    private HttpClientDownloader downloader = new HttpClientDownloader();

    private ApplicationConfig application = new ApplicationConfig();

    private RegistryConfig registry = new RegistryConfig();

    private IQuestionService questionService;

    private IAnswerService answerService;

    private IQuestionTagService questionTagService;


    public XiCaiWenDaSpider() {
        application.setName("test");
        registry.setAddress("redis://127.0.0.1:6379");

        questionService = getReferInstance(IQuestionService.class, registry, application).get();
        answerService = getReferInstance(IAnswerService.class, registry, application).get();
        questionTagService = getReferInstance(IQuestionTagService.class, registry, application).get();
    }


    public void spiderPage(String url) {
        Request request0 = new Request();
        request0.setMethod(HttpConstant.Method.GET);
        request0.setUrl(url);
        Page page0 = downloader.download(request0, site.toTask());
        Html html0 = page0.getHtml();
        List<String> links = html0.css(".qbwt_list").links().nodes().stream().map(Selectable::get).collect(Collectors.toList());
        links.stream().forEach(urll -> {

        });

        for (String link : links) {
            try {
                spiderDetail(link);
            } catch (Exception e) {

            }
        }
    }


    public void spiderDetail(String url) {
        Question question = new Question();
        Request request0 = new Request();
        request0.setMethod(HttpConstant.Method.GET);
        request0.setUrl(url);
        Page page0 = downloader.download(request0, site.toTask());
        Document contentDoc = Jsoup.parse(page0.getRawText());
        String title = contentDoc.select(".wd_title > h1").first().text();
        String content = "";
        Elements elements = contentDoc.select(".wd_title").first().getElementsByTag("p");
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            content = content + element.toString();
        }

        List<String> tags = Lists.newArrayList();
        iterator = contentDoc.select(".wd_bq a").iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            tags.add(element.text());
        }


        question.setTitle(title);
        question.setContent(content);
        question.setUserId("4246866490345472");
        question.setStatus(3);
        question.setAnswerCount(1);
        question.setTypeId("4368471723051008");
        question.setTags(tags);

        questionService.save(question);
        questionTagService.saveQuestionTag(tags);


        String answerStr = "";
        Elements elements1 = contentDoc.select(".wd_cn").first().getElementsByTag("p");
        iterator = elements1.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            answerStr = answerStr + element.toString();
        }

        Answer answer = new Answer();
        answer.setUserId("4248422253742080");
        answer.setQuestionId(question.getId());
        answer.setContent(answerStr);

        answerService.save(answer);

       answerService.acceptAnswer(answer.getId(), question.getId());

        System.out.println(title);


    }


    public static void main(String[] args) throws IOException {


        XiCaiWenDaSpider xiCaiWenDaSpider = new XiCaiWenDaSpider();
//        xiCaiWenDaSpider.spiderDetail("https://www.csai.cn/wenda/856473.html");

        for (int i = 1; i <= 16; i++) {
            xiCaiWenDaSpider.spiderPage("https://www.csai.cn/wenda/baoxian/2266-1-" + i + ".html");
        }


    }


}
