package com.manqian.mcollect;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtils {

    public static int max_count = 40000;

    public static void createSiteMapFile(Path path, List<Map<String, Object>> dataList) throws Exception {

        File file = path.toFile();
        if (file.exists()==false){
            file.createNewFile();
            builtXmlDocument(new ArrayList<Map<String, Object>>(), path.toString());
        }
        // 创建saxReader对象
        SAXReader reader = new SAXReader();
        // 通过read方法读取一个文件 转换成Document对象
        Document document = reader.read(file);
        //获取根节点元素对象
        Element node = document.getRootElement();

        List<Element> childNodes = node.elements();


        if(childNodes.size() > max_count){
            return;
        }

        Iterator<Map<String, Object>> iterator = dataList.iterator();

        int count = 0;
        while(iterator.hasNext()){
            if(childNodes.size() > max_count ){
                break;
            }
            Map<String, Object> tmpMap = iterator.next();
            childNodes.add(0, createElement(tmpMap));
            iterator.remove();
            count++;
        }
        System.out.println(String.format("%s add %d record , total %d", path,  count, childNodes.size()));
        //实例化输出格式对象
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
        writer.write(document);

    }

    public static Element createElement(Map<String, Object> m){
        Element url = DocumentHelper.createElement("url");
        Element loc = url.addElement("loc");
        Element lastmod = url.addElement("lastmod");
        Element changefreq = url.addElement("changefreq");
        Element priority = url.addElement("priority");
        if("h5".equals(TouTiaoSite.client)){
            loc.setText(String.format("http://m.toutiao.manqian.cn/wz_%s.html", m.get("id")));
        }else {
            loc.setText(String.format("http://toutiao.manqian.cn/wz_%s.html", m.get("id")));
        }

        lastmod.setText(m.get("date").toString());
        changefreq.setText("daily");
        priority.setText("0.4");
        return url;
    }

    public static void builtXmlDocument(List<Map<String, Object>> data, String path) throws IOException {
        Document doc = DocumentHelper.createDocument();
        //增加根节点
        Element urlset = doc.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
        urlset.addAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
        data.stream().forEach(m->{
            //增加子元素
            Element url = DocumentHelper.createElement("url");
            Element loc = url.addElement("loc");
            Element lastmod = url.addElement("lastmod");
            Element changefreq = url.addElement("changefreq");
            Element priority = url.addElement("priority");

            if("h5".equals(TouTiaoSite.client)){
                loc.setText(String.format("http://m.toutiao.manqian.cn/wz_%s.html", m.get("id")));
            }else {
                loc.setText(String.format("http://toutiao.manqian.cn/wz_%s.html", m.get("id")));
            }
            lastmod.setText(m.get("date").toString());
            changefreq.setText("daily");
            priority.setText("0.4");

            urlset.elements().add(0, url);

        });
        //实例化输出格式对象
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置输出编码
        format.setEncoding("UTF-8");
        //创建需要写入的File对象
        File file = new File(path.toString());
        //生成XMLWriter对象，构造函数中的参数为需要输出的文件流和格式
        XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
        writer.write(doc);
    }
}
