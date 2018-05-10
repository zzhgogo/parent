package com.zhuhao.utils;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Created by IntelliJ IDEA.
 * User: tsaowe
 * Date: 11-9-1
 * Time: 上午9:13
 */
public class XmlUtil {

    /**
     * xml文件配置转换为对象
     * @param xmlPath  xml文件路径
     * @param load    java对象.Class
     * @return    java对象
     * @throws JAXBException
     * @throws IOException
     */
    public static Object  xmlToBean(String xmlPath,Class<?> load){
        try{
            JAXBContext context = JAXBContext.newInstance(load);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Source source = new StreamSource(new ByteArrayInputStream(xmlPath.getBytes()));
            Object object = unmarshaller.unmarshal(source);
            return object;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * java对象转换为xml文件
     * @param load    java对象.Class
     * @return    xml文件的String
     * @throws JAXBException
     */
    public static String beanToXml(Object obj,Class<?> load){
        try{
            JAXBContext context = JAXBContext.newInstance(load);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj,writer);
            return writer.toString();
        }catch (Exception e){
            return null;
        }
    }

    public static String format(String str) throws Exception {
        try {
            SAXReader reader = new SAXReader();
            // System.out.println(reader);
            // 注释：创建一个串的字符输入流
            StringReader in = new StringReader(str);
            Document doc = reader.read(in);
            // System.out.println(doc.getRootElement());
            // 注释：创建输出格式
            OutputFormat formater = OutputFormat.createPrettyPrint();
            //formater=OutputFormat.createCompactFormat();
            // 注释：设置xml的输出编码
            formater.setEncoding("utf-8");
            // 注释：创建输出(目标)
            StringWriter out = new StringWriter();
            // 注释：创建输出流
            XMLWriter writer = new XMLWriter(out, formater);
            // 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中。
            writer.write(doc);
            writer.close();
            // 注释：返回我们格式化后的结果
            return out.toString();
        }catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args) throws Exception{


    }
}