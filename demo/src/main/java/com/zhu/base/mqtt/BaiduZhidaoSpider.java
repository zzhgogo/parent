package com.zhu.base.mqtt;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.google.common.collect.Lists;
import com.manqian.toutiao.answer.api.IAnswerService;
import com.manqian.toutiao.answer.api.IQuestionService;
import com.manqian.toutiao.answer.api.IQuestionTagService;
import com.manqian.toutiao.common.page.PageImpl;
import com.manqian.toutiao.entity.Answer;
import com.manqian.toutiao.entity.Question;
import com.zhu.base.dubbo.ToutiaoDubbo;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.junit.Test;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:zhuhao
 * @Description: 搜狗微信文章采集
 * @Date:
 **/
public class BaiduZhidaoSpider extends ToutiaoDubbo {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Site site = Site.me()
            .setTimeOut(1000 * 15)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
            .setRetryTimes(8);

    private HttpClientDownloader downloader = new HttpClientDownloader();

    public List<String> spider(String url) {
        Request request0 = new Request();
        request0.setMethod(HttpConstant.Method.GET);
        request0.setUrl(url);
        Page page0 = downloader.download(request0, site.toTask());
        Html html0 = page0.getHtml();

        List<Selectable> selectables = html0.$(".question-title a").nodes();

        List<String> titles = selectables.stream().map(selectable -> selectable.xpath("/a/text()").get()).collect(Collectors.toList());

//        titles.stream().forEach(System.out::println);
        return titles;


    }

    public List<String> tern(String string) {
        List<Term> parse = BaseAnalysis.parse(string).getTerms();
        return parse.stream().map(Term::getName).filter(m -> m.length() > 1).limit(3).collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {


    }


    @Test
    public void t1() {
        String url = "https://iask.sina.com.cn/c/78.html";
        // 普通编码配置方式
        ApplicationConfig application = new ApplicationConfig();
        application.setName("test");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("redis://127.0.0.1:6379");


        IQuestionService questionService = getReferInstance(IQuestionService.class, registry, application).get();
        IAnswerService answerService = getReferInstance(IAnswerService.class, registry, application).get();
        IQuestionTagService questionTagService = getReferInstance(IQuestionTagService.class, registry, application).get();

        BaiduZhidaoSpider spider = new BaiduZhidaoSpider();
        List<String> titles = spider.spider(url);

        List<Question> questionList = Lists.newArrayList();

        for (String title : titles) {
            Question question = new Question();
            List<String> tags = spider.tern(title);
            question.setTitle(title);
            question.setContent(title);
            question.setUserId("4246866490345472");
            question.setStatus(3);
            question.setAnswerCount(5);
            question.setTypeId("4368471723051008");
            question.setTags(tags);
            questionList.add(question);

            List<Answer> answers = new ArrayList<>();
            for (int t = 0; t < 5; t++) {
                Answer answer = new Answer();
                answer.setUserId("4226944653346816");
                answer.setQuestionId(question.getId());
                answer.setContent("测试答案-" + t);
                answers.add(answer);
            }
            questionService.save(question);
            questionTagService.saveQuestionTag(tags);
            answerService.save(answers);
            answerService.acceptAnswer(answers.get(0).getId(), question.getId());
            System.out.println(title);
        }

        List<String> questIds = questionService.queryPage(new PageImpl(1, 1000)).getContent().stream().map(Question::getId).collect(Collectors.toList());


        System.out.println("over");

    }


}
