package com.zhu.base.mqtt;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.manqian.toutiao.answer.api.IAnswerService;
import com.manqian.toutiao.answer.api.IQuestionService;
import com.manqian.toutiao.answer.api.IQuestionTagService;
import com.manqian.toutiao.entity.Answer;
import com.manqian.toutiao.entity.Question;
import com.zhu.base.dubbo.ToutiaoDubbo;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author:zhuhao
 * @Description: 搜狗微信文章采集
 * @Date:
 **/
public class BaiduZhidaoSpider extends ToutiaoDubbo {

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

    public BaiduZhidaoSpider() {
        application.setName("test");
        registry.setAddress("redis://127.0.0.1:6379");

        questionService = getReferInstance(IQuestionService.class, registry, application).get();
        answerService = getReferInstance(IAnswerService.class, registry, application).get();
        questionTagService = getReferInstance(IQuestionTagService.class, registry, application).get();
    }


    public List<String> spider(String url) {
        Request request0 = new Request();
        request0.setMethod(HttpConstant.Method.GET);
        request0.setUrl(url);
        Page page0 = downloader.download(request0, site.toTask());
        Html html0 = page0.getHtml();

        List<Selectable> selectables = html0.$(".article-item a").links().nodes();

        List<String> urls = selectables.stream().map(Selectable::get).collect(Collectors.toList());

        urls.stream().forEach(System.out::println);

        return urls;


    }


    public void spiderDetail(String url) {
        Question question = new Question();
        Request request0 = new Request();
        request0.setMethod(HttpConstant.Method.GET);
        request0.setUrl(url);
        Page page0 = downloader.download(request0, site.toTask());
        Document contentDoc = Jsoup.parse(page0.getRawText());
        String title = contentDoc.select(".article-title").first().text();


        question.setTitle(title);
        question.setContent("");
        question.setUserId("4243998835853312");
        question.setStatus(3);
        question.setUpdateTime(new Date());
        question.setAnswerCount(1);
        question.setTypeId("4368468671088640");
        question.setTags(new ArrayList<>());

        questionService.save(question);



        String content = contentDoc.select(".article-content").first().html();


        Answer answer = new Answer();
        answer.setUserId("4243998835853312");
        answer.setQuestionId(question.getId());
        answer.setContent(content);

        answerService.save(answer);

        answerService.acceptAnswer(answer.getId(), question.getId());

        System.out.println(title);


    }

    public void f() {
        List<Answer> list = answerService.findAll();

        list.forEach(question -> {
            Document contentDoc = Jsoup.parse(question.getContent());
            Elements elements = contentDoc.getElementsByTag("a");
            Iterator<Element> iterator = elements.iterator();
            while (iterator.hasNext()){
                Element element = iterator.next();
                element.remove();
            }
            question.setContent(contentDoc.toString());
            answerService.save(question);
        });




    }


    public static void main(String[] args) {
        BaiduZhidaoSpider spider = new BaiduZhidaoSpider();
        spider.f();
        Stream.iterate(1, i->i+1).limit(10).forEach(i->{
           List<String> urls = spider.spider("https://jin.baidu.com/ask?pn="+i);
           if(CollectionUtils.isNotEmpty(urls)){
               urls.forEach(url->{
                   try {
                       spider.spiderDetail(url);
                   }catch (Exception e){

                   }
               });
           }

        });
    }





}
