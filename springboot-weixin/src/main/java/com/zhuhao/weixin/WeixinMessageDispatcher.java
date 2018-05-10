package com.zhuhao.weixin;

import com.zhuhao.utils.StringUtil;
import com.zhuhao.utils.WeChatUtil;
import com.zhuhao.utils.WeixinResponseEncoder;
import com.zhuhao.weixin.message.MessageType;
import com.zhuhao.weixin.message.TextMessage;
import com.zhuhao.weixin.message.WeixinMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;


@Component
public class WeixinMessageDispatcher {

    private final static Logger logger = LoggerFactory.getLogger(WxController.class);


    public String doDispatch(HttpServletRequest request) throws Exception{
        InputStream inputStream = request.getInputStream();
        String xmlStr = StringUtil.inputStreamToString(inputStream);
        WeixinMessage weixinMessage = WeChatUtil.messageRead(xmlStr, WeixinMessage.class);
        if(weixinMessage.getFormatMsgType()==MessageType.text){
            TextMessage textMessage = WeChatUtil.messageRead(xmlStr, TextMessage.class);
            logger.info(textMessage.toString());
        }else if(weixinMessage.getFormatMsgType()==MessageType.image){
            logger.info(xmlStr);
        }
        return WeixinResponseEncoder.encode(weixinMessage);
    }



}
