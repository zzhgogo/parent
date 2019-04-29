package com.zhu.base.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


// https://www.cnblogs.com/doudouxiaoye/p/5693441.html
public class Xml {

    public static void main(String[] args) throws Exception {
        new Xml().cc();
//        // 创建DocumentBuilderFactory
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//
//        // 创建DocumentBuilder
//        DocumentBuilder builder = factory.newDocumentBuilder();
//
//        // 创建Document
//        Document document = builder.newDocument();
//
//        // 创建根节点
//        Element bookstore = document.createElement("bookstore");
//
//        // 创建子节点，并设置属性
//        Element book = document.createElement("book");
//        book.setAttribute("id", "1");
//
//        // 为book添加子节点
//        Element name = document.createElement("name");
//        name.setTextContent("安徒生童话");
//        book.appendChild(name);
//        Element author = document.createElement("author");
//        author.setTextContent("安徒生");
//        book.appendChild(author);
//        Element price = document.createElement("price");
//        price.setTextContent("49");
//        book.appendChild(price);
//
//        // 为根节点添加子节点
//        bookstore.appendChild(book);
//
//        // 将根节点添加到Document下
//        document.appendChild(bookstore);
//
//        // 创建TransformerFactory对象
//        TransformerFactory tff = TransformerFactory.newInstance();
//
//        // 创建Transformer对象
//        Transformer tf = tff.newTransformer();
//
//        // 使用Transformer的transform()方法将DOM树转换成XML
//        tf.transform(new DOMSource(document), new StreamResult("d:\\bb.xml"));
    }

    public void cc(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //创建一个新的Document对象。并不是获取
            Document xmlDocument = builder.newDocument();
            //创建根节点并加入属性值
            Element root = xmlDocument.createElement("Languages");
            root.setAttribute("foo", "bar");

            /**
             * 创建第一个元素节点
             */
            //创建对应的元素节点，并加入属性值和文本内容
            Element lan_1 = xmlDocument.createElement("lan");
            lan_1.setAttribute("id", "1");
            Element name_1 = xmlDocument.createElement("name");
            name_1.setTextContent("Java");
            Element ide_1 = xmlDocument.createElement("ide");
            ide_1.setTextContent("Eclipse");
            //将name标签和ide标签加入到lan标签内
            lan_1.appendChild(name_1);
            lan_1.appendChild(ide_1);
            //将lan标签加入到Languages标签内
            root.appendChild(lan_1);

            /**
             * 创建第二个元素节点
             */
            //创建对应的元素节点，并加入属性值和文本内容
            Element lan_2 = xmlDocument.createElement("lan");
            lan_2.setAttribute("id", "2");
            Element name_2 = xmlDocument.createElement("name");
            name_2.setTextContent("Swift");
            Element ide_2 = xmlDocument.createElement("ide");
            ide_2.setTextContent("XCode");
            //将name标签和ide标签加入到lan标签内
            lan_2.appendChild(name_2);
            lan_2.appendChild(ide_2);
            //将lan标签加入到Languages标签内
            root.appendChild(lan_2);

            //将根节点加入进Document文档对象中
            xmlDocument.appendChild(root);

            //对XML数据进行输出须要进行转换，使用Transformer
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", new Integer(2));
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("encoding", "UTF-8");


            //创建新的字符输出流用于输出数据
            StringWriter stringWriter = new StringWriter();
            //对XML文档 对象进行转换并输出到输出流中
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(stringWriter));


            System.out.println(stringWriter.toString());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public String format(String unformattedXml) {
       return null;
    }





}
