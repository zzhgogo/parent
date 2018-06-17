package com.zhu.base;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Xml {

    public static void main(String[] args) throws Exception {
        // 创建DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建DocumentBuilder
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 创建Document
        Document document = builder.newDocument();

        // 创建根节点
        Element bookstore = document.createElement("bookstore");

        // 创建子节点，并设置属性
        Element book = document.createElement("book");
        book.setAttribute("id", "1");

        // 为book添加子节点
        Element name = document.createElement("name");
        name.setTextContent("安徒生童话");
        book.appendChild(name);
        Element author = document.createElement("author");
        author.setTextContent("安徒生");
        book.appendChild(author);
        Element price = document.createElement("price");
        price.setTextContent("49");
        book.appendChild(price);

        // 为根节点添加子节点
        bookstore.appendChild(book);

        // 将根节点添加到Document下
        document.appendChild(bookstore);

        // 创建TransformerFactory对象
        TransformerFactory tff = TransformerFactory.newInstance();

        // 创建Transformer对象
        Transformer tf = tff.newTransformer();

        // 使用Transformer的transform()方法将DOM树转换成XML
        tf.transform(new DOMSource(document), new StreamResult("/data/bb.xml"));
    }

}
