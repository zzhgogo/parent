package com.zhu.base.selenium;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.manqian.mcollect.entity.OfficialAccount;
import com.manqian.mcollect.entity.vo.ArticleVo;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author:zhuhao
 * @Description: 搜狗微信文章采集
 * @Date:
 **/
public class MpWeiXinArticleSpider {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Site site = Site.me()
            .setTimeOut(1000 * 15)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
            .setRetryTimes(8).setDomain("mp.weixin.qq.com");

    private HttpClientDownloader downloader = new HttpClientDownloader();


    /**
     * 根据文章链接采集文章
     *
     * @param url
     */
    public Map<String, Object> crawlDetail(String url) {
        Request request0 = new Request();
        request0.setMethod(HttpConstant.Method.GET);
        request0.setUrl(url);
        Page page0 = downloader.download(request0, site.toTask());
        Document contentDoc = Jsoup.parse(page0.getRawText());

        String title = getTitle(contentDoc);
        String content = getContent(contentDoc);
        String originUrl = getOriginUrl(page0.getRawText());
        String converUrl = getConverUrl(page0.getRawText());

        Map<String, Object> rmap = new HashMap<>();

        ArticleVo articleVo = new ArticleVo();
        articleVo.setTitle(title);
        articleVo.setContent(content);
        articleVo.setOriginalUrl(originUrl);
        articleVo.setCoverUrl(converUrl);

        OfficialAccount account = getArticleDetailAccountInfo(contentDoc);

        rmap.put("articleVo", articleVo);
        rmap.put("account", account);

        return rmap;


    }

    private String getOriginUrl(String html) {
        String src = regexField("src", html);
        String ver = regexField("ver", html);
        String timestamp = regexField("timestamp", html);
        String signature = regexField("signature", html);
        return "https://mp.weixin.qq.com/s?src=" + src + "&timestamp=" + timestamp + "&ver=" + ver + "&signature=" + signature + "&new=1";
    }


    private String getContent(Document contentDoc) {
        Element c_element = contentDoc.select("#js_content").first();
        clearAttr(c_element, "img", Lists.newArrayList("src", "style", "data-src"));
        clearAttr(c_element, "p", Lists.newArrayList(""));
        clearAttr(c_element, "section", Lists.newArrayList(""));
        return c_element.html();
    }

    private String getTitle(Document contentDoc) {
        Element t_element = contentDoc.select("#activity-name").first();
        return t_element.text();
    }

    private String getConverUrl(String pageSource) {
        Pattern pattern = Pattern.compile("(?<= var msg_cdn_url = \").*?(?=\";)");
        Matcher matcher = pattern.matcher(pageSource);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 过滤属性
     *
     * @param parent
     * @param tagName
     * @param attrkeys
     */
    private void clearAttr(Element parent, String tagName, List<String> attrkeys) {
        for (Element element : parent.getElementsByTag(tagName)) {
            List<String> exitKeys = element.attributes().asList().stream().map(Attribute::getKey).filter(key -> !attrkeys.contains(key)).collect(Collectors.toList());
            exitKeys.stream().forEach(element::removeAttr);
            if (element.hasAttr("data-src")) {
                element.attr("src", "/img?url=" + element.attr("data-src"));
                element.removeAttr("data-src");
            }
        }
        parent.getElementsByAttribute("style").stream().forEach(element -> element.removeAttr("style"));
        parent.getElementsByTag("br").stream().forEach(Element::remove);
    }


    private String regexField(String filed, String pageSource) {
        Pattern pattern = Pattern.compile("(?<= " + filed + ":\").*?(?=\")");
        Matcher matcher = pattern.matcher(pageSource);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }


    // 获取公众号信息
    private OfficialAccount getArticleDetailAccountInfo(Document contentDoc) {
        try {
            OfficialAccount account = new OfficialAccount();
            Element content = contentDoc.select("#js_profile_qrcode").first();
            String weChatID = content.select(".profile_meta_value").first().text();
            String name = content.select(".profile_nickname").first().text();
            String introduce = content.select(".profile_meta_value").get(1).text();
            if (StringUtils.isBlank(weChatID)) { //获取公众号id
                Pattern pattern = Pattern.compile("(?<=var user_name = \").*?(?=\";)");
                Matcher matcher = pattern.matcher(contentDoc.html());
                if (matcher.find()) {
                    weChatID = matcher.group();
                }
            }
            String qr = "http://open.weixin.qq.com/qr/code/?username=" + weChatID;
            account.setqRCodeUrl(qr);
            account.setWeChatID(weChatID);
            account.setIntroduce(introduce);
            account.setName(name);
            return account;
        } catch (Exception e) {
            logger.error("获取公众号信息失败", e);
            return null;
        }
    }

}
