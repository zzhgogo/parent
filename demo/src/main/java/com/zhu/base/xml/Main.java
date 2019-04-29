package com.zhu.base.xml;

import com.thoughtworks.xstream.XStream;
import com.zhu.base.xml.urlset.Display;
import com.zhu.base.xml.urlset.Url;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuhao
 * @title: Main
 * @projectName parent
 * @description: TODO
 * @date 2019/4/289:52 AM
 */
public class Main {

    @Test
    public void t1() {

        Url url = new Url();
        url.setLoc("http://baidu.com/");
        url.setLastmod("2018-04-28");
        url.setChangefreq("always");
        url.setPriority("1.0");

        Display display = new Display();
        display.setContext("https://ziyuan.baidu.com/contexts/cambrian.jsonld");
        display.setId("http://toutiao.manqian.cn/videos/4249979576223744.html");
        display.setAppid("1601784359111516");
        display.setTitle("免费约车再度来袭？美团手撕滴滴，携程高调入局");
        display.setImages("https://pic.manqian.cn/8c0e94c4-494b-428b-bf5a-b52a8a559af2?t=1548233107322");
        display.setPubDate("2018-06-15T08:00:01");
        display.setUpDate("2018-06-15T08:00:01");
        display.setLrDate("2018-06-15T08:00:01");
        display.setDescription("对打车市场来说，需要公平合理的竞争环境，完善的规章制度，还有一个永远能抗衡的老二");

        url.getData().setDisplay(display);




        //创建解析XML对象
        XStream xStream = new XStream();

        //设置别名, 默认会输出全路径
        xStream.alias("url", Url.class);
        //转为xml
        String xml = xStream.toXML(url);
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<urlset content_method=\"full\">\n" + xml + "\n</urlset>";
        System.out.println(s);

    }
}
