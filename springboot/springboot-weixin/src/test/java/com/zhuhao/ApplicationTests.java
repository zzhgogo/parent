package com.zhuhao;

import com.alibaba.fastjson.JSONObject;
import com.zhuhao.consts.WeixinUrl;
import com.zhuhao.utils.HttpClientUtil;
import com.zhuhao.utils.WeChatUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

	@Test
	public void contextLoads() {
        JSONObject obj = JSONObject.parseObject(getErCode());
        logger.info("二维码：{}", obj.getString("url"));
	}

	/**
	 * 获取accessToken
	 */
	public static String getAccessToken() {
		String url = WeixinUrl.GET_ACCESSTOKEN
				.replace("APPID", "wxa4cc0a202b65477c")
				.replace("APPSECRET", "29d8103ec273d5af766ed8ba7eaec315");
		String resultJson = HttpClientUtil.doGet(url);
		JSONObject jsonObject = JSONObject.parseObject(resultJson);
		return jsonObject.getString("access_token");
	}


	public static String getErCode(){
		String jsonStr = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
		String accesstoken = getAccessToken();
		String url = WeixinUrl.GER_ERCDOE_WITH_PARAM_URL.replace("TOKEN", accesstoken);
		String resultJson = HttpClientUtil.doPost(url, jsonStr);
		logger.info(resultJson);
		JSONObject obj = JSONObject.parseObject(resultJson);
		String url2 = WeixinUrl.GER_ERCDOE_BY_TICKET.replace("TICKET", obj.getString("ticket"));
		logger.info(url2);
		HttpClientUtil.doGet(url2);
		return resultJson;
	}

}
