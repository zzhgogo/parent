package com.zhu.base.mqtt;

import com.redfin.sitemapgenerator.*;
import org.assertj.core.util.Files;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhuhao
 * @title: SitemapGeneratorExample
 * @projectName parent
 * @description: TODO
 * @date 2019/4/1711:10 AM
 */
public class SitemapGeneratorExample {


    public static void main(String[] args) throws MalformedURLException, ParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // If you need gzipped output
        WebSitemapGenerator sitemapGenerator = WebSitemapGenerator
                .builder("http://www.javatips.net", new File("/data/sitemap"))
                .gzip(false).build();

        WebSitemapUrl sitemapUrl = new WebSitemapUrl.Options("http://www.javatips.net/blog/2011/08/findbugs-in-eclipse-java-tutorial")
                .lastMod(dateTimeFormatter.format(LocalDateTime.now())).priority(1.0)
                .changeFreq(ChangeFreq.HOURLY).build();
        // this will configure the URL with lastmod=now, priority=1.0,
        // changefreq=hourly

        // You can add any number of urls here
        sitemapGenerator.addUrl(sitemapUrl);

        sitemapGenerator.write();
    }

    @Test
    public void generateSitemap() {
        String tempPath = "/data/sitemap/";


        File file = new File(tempPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String domain = "https://www.domain.com";
        try {
            WebSitemapGenerator g1 = WebSitemapGenerator.builder(domain, file)
                    .fileNamePrefix("article").build();
            Date date = new Date();
            for (int i = 1; i < 1000000; i++) {
                WebSitemapUrl sitemapUrl = new WebSitemapUrl.Options(domain + "/article/" + i )
                        .lastMod(date).priority(1.0)
                        .changeFreq(ChangeFreq.HOURLY)
                        .build();
                g1.addUrl(sitemapUrl);
            }

            // 生成 sitemap 文件
            List<File> articleFiles = g1.write();

            List<String> fileNames = articleFiles.stream().map(File::getName).collect(Collectors.toList());


            // 构造 sitemap_index 生成器
            W3CDateFormat dateFormat = new W3CDateFormat(W3CDateFormat.Pattern.DAY);
            SitemapIndexGenerator sitemapIndexGenerator = new SitemapIndexGenerator
                    .Options(domain, new File(tempPath + "sitemap_index.xml"))
                    .dateFormat(dateFormat)
                    .autoValidate(true)
                    .build();
            fileNames.forEach(e -> {
                try {
                    // 组装 sitemap 文件 URL 地址
                    sitemapIndexGenerator.addUrl(domain+"/" + e);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            });
            // 生成 sitemap_index 文件
            sitemapIndexGenerator.write();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
