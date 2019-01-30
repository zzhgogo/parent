package com.zhu.base.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.google.common.collect.Lists;
import com.manqian.mcollect.api.IUserService;
import com.manqian.mcollect.api.article.IArticleContentService;
import com.manqian.mcollect.api.article.IWeChatArticleService;
import com.manqian.mcollect.entity.User;
import com.manqian.mcollect.entity.article.WeChatArticle;
import com.manqian.mcollect.paging.PageableImpl;
import com.manqian.toutiao.business.api.IArticleCatalogueService;
import com.manqian.toutiao.business.api.IArticleChannelService;
import com.manqian.toutiao.business.api.IArticleService;
import com.manqian.toutiao.business.api.IArticleTagService;
import com.manqian.toutiao.business.entity.Article;
import com.manqian.toutiao.business.entity.ArticleCatalogue;
import com.manqian.toutiao.business.entity.ArticleChannel;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:zhuhao
 * @Description:
 * @Date:
 **/
public class ToutiaoDubbo {


    public static <T> ReferenceConfig<T> getReferInstance(Class<T> tClass, RegistryConfig registryConfig, ApplicationConfig applicationConfig){
        ReferenceConfig<T> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setTimeout(100000);
        referenceConfig.setInterface(tClass);
        referenceConfig.setVersion("1.0.0");
        return referenceConfig;
    }


    @Test
    public void t1(){

        // 普通编码配置方式
        ApplicationConfig application = new ApplicationConfig();
        application.setName("test");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("redis://127.0.0.1:6379");


        IArticleService  articleService = getReferInstance(IArticleService.class, registry ,application).get();

        IArticleTagService articleTagService = getReferInstance(IArticleTagService.class, registry ,application).get();

        IUserService userService = getReferInstance(IUserService.class, registry ,application).get();


        IArticleChannelService articleChannelService = getReferInstance(IArticleChannelService.class, registry ,application).get();
        IArticleCatalogueService catalogueService = getReferInstance(IArticleCatalogueService.class, registry ,application).get();
        IWeChatArticleService weChatArticleService = getReferInstance(IWeChatArticleService.class, registry ,application).get();
        IArticleContentService articleContentService = getReferInstance(IArticleContentService.class, registry ,application).get();

        Page<WeChatArticle> page = weChatArticleService.getLatestArticlePage(new PageableImpl(5, 15));



        List<String> catalogueIds = catalogueService.findAll().stream().map(ArticleCatalogue::getId).collect(Collectors.toList());
        List<String> userIds = userService.findNormalAll().stream().map(User::getId).collect(Collectors.toList());
        List<String> channelIds = articleChannelService.findAll().stream().map(ArticleChannel::getId).collect(Collectors.toList());
        page.getContent().stream().forEach(weChatArticle -> {
            Article article = new Article();
            article.setTags(weChatArticle.getTags());
            article.setTitle(weChatArticle.getTitle());
            article.setDescription(weChatArticle.getTitle());
            article.setCoverUrls(Lists.newArrayList(weChatArticle.getCoverUrl()));
            article.setOriginalUrl(weChatArticle.getOriginalUrl());
            article.setContent(articleContentService.findByArticleId(weChatArticle.getId()).getContent());
            Collections.shuffle(userIds);
            article.setUserId(userIds.get(0));
//            Collections.shuffle(channelIds);
//            article.setChannelId(channelIds.get(0));

            Collections.shuffle(catalogueIds);
            article.setCatalogue(catalogueIds.get(0));
            articleService.save(article);
            articleTagService.saveTag(weChatArticle.getTags());
        });


    }

}
