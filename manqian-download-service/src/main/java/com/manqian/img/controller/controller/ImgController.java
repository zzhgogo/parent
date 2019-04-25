package com.manqian.img.controller.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class ImgController {

    private static Logger logger = LoggerFactory.getLogger(ImgController.class);

    private static Map<String, String> headers = new HashMap<String, String>() {{
        put("Connection", "keep-alive");
        put("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        put("Accept-Encoding", "gzip, deflate, br");
        put("Accept-Language", "zh-CN,zh;q=0.9");
        put("Cache-Control", "no-cache");
        put("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36");
    }};


    @RequestMapping(value = "/img", method = RequestMethod.GET)
    public void downLoadImg(@RequestParam(required = false) String url, HttpServletResponse response) {
        if (StringUtils.isBlank(url)) {
            return;
        }
        if (url.contains("?")) {
            url = url.substring(0, url.lastIndexOf("?"));
        }
        // 指定url可访问 不然会有安全漏洞问题
//        if (!url.contains("mmbiz") &&
//                !url.contains("mmhead") &&
//                !url.contains("mmsns") &&
//                !url.contains("images.tmtpost.com") &&
//                !url.contains("mmbiz.qlogo.cn") &&
//                !url.contains("mmbiz.qpic.cn") &&
//                !url.contains("wx.qlogo.cn") &&
//                !url.contains("wx.qpic.cn") &&
//                !url.contains("img") &&
//                !url.contains("png") &&
//                !url.contains("gif") &&
//                !url.contains("jpg") &&
//                !url.contains("mmsns.qpic.cn") &&
//                !url.contains("manqian.cn")) {
//            return;
//        }

        if (url.startsWith("https://") || url.startsWith("http://")) {

        } else if (url.startsWith("https:/")) {
            url = url.replaceFirst("https:/", "https://");
        } else if (url.startsWith("http:/")) {
            url = url.replaceFirst("http:/", "http://");
        } else if (url.startsWith("//")) {
            url = url.replaceFirst("//", "http://");
        } else {
            url = "http://" + url;
        }

        OutputStream os = null;

        try {
            response.setContentType("image/jpeg");
            os = response.getOutputStream();
            downLoadImgToPage(url, os);
        } catch (IOException e) {
            logger.error("downLoadImg failed,  url:{}", url);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {

                }
            }
        }

    }

    /**
     * 下载网络图片
     *
     * @param fileurl
     * @param os
     */
    private void downLoadImgToPage(String fileurl, OutputStream os) {
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        InputStream in = null;
        try {
            URL url = new URL(fileurl);
            URLConnection conn = url.openConnection();
            Set<String> headerskey = headers.keySet();
            for (String headerkey : headerskey) {
                conn.setRequestProperty(headerkey, headers.get(headerkey));
            }
            conn.setUseCaches(false);
            in = conn.getInputStream();
            bis = new BufferedInputStream(in, 1024 * 1024);//设置读入缓存为1M
            bos = new BufferedOutputStream(os, 1024 * 1024);//设置写缓存为1M
            byte[] b = new byte[1024 * 1024];
            int i = 0;
            while ((i = bis.read(b)) != -1) {
                bos.write(b, 0, i);
            }
            logger.debug("downLoadImgToPage success,  url:{}", fileurl);
        } catch (IOException e) {
            logger.error("downLoadImgToPage failed,  url:{}", fileurl);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    logger.error("downLoadImgToPage failed,  url:{}", fileurl);
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error("downLoadImgToPage failed,  url:{}", fileurl);
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("downLoadImgToPage failed,  url:{}", fileurl);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("downLoadImgToPage failed,  url:{}", fileurl);
                }
            }
        }
    }
}
