package com.zhuhao.utils;


import com.zhuhao.weixin.message.WeixinMessage;
import javax.xml.bind.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeChatUtil {

    /**
     * 消息转换
     */
    private  static Map<Class<? extends WeixinMessage>, Unmarshaller> messageUnmarshaller = new ConcurrentHashMap<Class<? extends WeixinMessage>, Unmarshaller>();

    /**
     * jaxb读取微信消息
     *
     * @param message
     *            xml消息
     * @param clazz
     *            消息类型
     * @return 消息对象 @
     */
    public  static <M extends WeixinMessage> M messageRead(String message, Class<M> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            Source source = new StreamSource(new ByteArrayInputStream(message.getBytes("UTF-8")));
            JAXBElement<M> jaxbElement = getUnmarshaller(clazz).unmarshal(source, clazz);
            return jaxbElement.getValue();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * xml消息转换器
     *
     * @param clazz
     *            消息类型
     * @return 消息转换器 @
     */
    protected static Unmarshaller getUnmarshaller(Class<? extends WeixinMessage> clazz) {
        Unmarshaller unmarshaller = messageUnmarshaller.get(clazz);
        if (unmarshaller == null) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                unmarshaller = jaxbContext.createUnmarshaller();
                messageUnmarshaller.put(clazz, unmarshaller);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return unmarshaller;
    }

    /**
     * JavaBean转换成xml
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml(WeixinMessage m, String encoding) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(m.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            StringWriter writer = new StringWriter();
            marshaller.marshal(m, writer);
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
