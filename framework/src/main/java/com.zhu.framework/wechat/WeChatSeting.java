package wechat;

import com.zhu.framework.wechat.MediaUtil;
import net.sf.json.JSONObject;
import util.HttpClientUtil;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * Author: as
 * Time:  2017/9/28 9:05
 * Description: 微信菜单设置
 */
public class WeChatSeting {

    /**
     * 微信公众号唯一标识
     */
    public static final String APPID ="wx3ff89b6b9577c419";

    /**
     * 微信公众号的appsecret
     */
    public static final String APPSECRET = "05f62ac73bf46b3ead693329004d0725";


    /**
     * 菜单创建-微信接口
     */
    public static final String CREATE_NORMAL_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /**
     * 获取accessToken
     */
    public static final String GET_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    public static final String file_path = "/1.txt";


   public static void main(String[] args) throws Exception{
       String accessToken = getAccessToken();
       String string = MediaUtil.uploadMedia(accessToken, "image/jpg", "http://mmbiz.qpic.cn/mmbiz_jpg/lseiciadpq29ibM1ZzVJZlq5N8kVyYrEtnicN0K8wFyhJ0xQWE5oEQbd1CogAR8CQWTNYZgy7WGtcuglLia2mlbUbow/0");
       System.out.println(string);
   }

    /**
     * 初始化微信菜单
     * @param jsonStr
     * @return
     * @throws Exception
     */
    public static void initMenu(String jsonStr) throws Exception{
        String accessToken = getAccessToken();
        String url = WeChatSeting.CREATE_NORMAL_MENU_URL.replace("ACCESS_TOKEN", accessToken);
        String resultJson = HttpClientUtil.doPost(url, jsonStr);
        System.out.println(resultJson);
    }

    /**
     * 获取accessToken
     */
    public static String getAccessToken() throws Exception {
        String url = WeChatSeting.GET_ACCESSTOKEN
                .replace("APPID", WeChatSeting.APPID)
                .replace("APPSECRET", WeChatSeting.APPSECRET);
        String resultJson = HttpClientUtil.doGet(url);
        JSONObject jsonObject = JSONObject.fromObject(resultJson);
        return jsonObject.getString("access_token");
    }



}
