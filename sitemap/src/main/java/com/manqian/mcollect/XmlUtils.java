package com.manqian.mcollect;

import java.io.*;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtils {


    public static int max_count = 800;

    public static void main(String[] args) throws IOException {


    }

    public static void readXMLDemo(Path path, List<Map<String, Object>> dataList) throws Exception {
        // 创建saxReader对象
        SAXReader reader = new SAXReader();
        // 通过read方法读取一个文件 转换成Document对象
        Document document = reader.read(new File(path.toString()));
        //获取根节点元素对象
        Element node = document.getRootElement();

        List<Element> childNodes = node.elements();

        System.out.println(String.format("%s has %d record", path, childNodes.size()));

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

        System.out.println(String.format("%s add %d record", path, count));

        //实例化输出格式对象
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        File file = new File(path.toString());
        XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
        writer.write(document);
    }

    public static Element createElement(Map<String, Object> m){
        Element url = DocumentHelper.createElement("url");
        Element loc = url.addElement("loc");
        Element lastmod = url.addElement("lastmod");
        Element changefreq = url.addElement("changefreq");
        Element priority = url.addElement("priority");

        loc.setText(String.format("http://toutiao.manqian.cn/wz_%s.html", m.get("id")));
        lastmod.setText(m.get("date").toString());
        changefreq.setText("daily");
        priority.setText("0.4");
        return url;
    }

    public static void listNodes(Element node) {
        System.out.println("当前节点的名称：：" + node.getName());
        // 获取当前节点的所有属性节点
        List<Attribute> list = node.attributes();
        // 遍历属性节点
        for (Attribute attr : list) {
            System.out.println(attr.getText() + "-----" + attr.getName()
                    + "---" + attr.getValue());
        }

        if (!(node.getTextTrim().equals(""))) {
            System.out.println("文本内容：：：：" + node.getText());
        }

        // 当前节点下面子节点迭代器
        Iterator<Element> it = node.elementIterator();
        // 遍历
        while (it.hasNext()) {
            // 获取某个子节点对象
            Element e = it.next();
            // 对子节点进行遍历
            listNodes(e);
        }
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

            loc.setText(String.format("http://toutiao.manqian.cn/wz_%s.html", m.get("id")));
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
