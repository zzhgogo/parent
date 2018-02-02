package wechat;

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
    public static final String APPID ="wxa4cc0a202b65477c";

    /**
     * 微信公众号的appsecret
     */
    public static final String APPSECRET = "29d8103ec273d5af766ed8ba7eaec315";

    /**
     * 菜单创建-微信接口
     */
    public static final String CREATE_NORMAL_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /**
     * 获取accessToken
     */
    public static final String GET_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    public static final String file_path = "/1.txt";


   public static void main(String[] args){
       StringBuilder result = new StringBuilder();
       try{
           BufferedReader br = new BufferedReader(new FileReader(WeChatSeting.class.getResource(file_path).getFile()));
           String s = null;
           while((s = br.readLine())!=null){
               result.append(System.lineSeparator()+s);
           }
           br.close();
           WeChatSeting.initMenu(result.toString());
       }catch(Exception e){
           e.printStackTrace();
       }
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
