package com.hp.mobile.utils;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.hp.redis.utils.RedisUtils;

 

public class WxApiUtils {

	@Autowired

	private static RestTemplate restTemplete;

	@Autowired

	private static RedisUtils redisUtils;
	@Value("${wx.appId}")
	private  static String appId;
	@Value("${wx.secret}")
	private static  String secret;

	private static final Logger Log = LoggerFactory.getLogger(WxApiUtils.class);

	/**
	 * 获取微信小程序 session_key 和 openid
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> getSessionKeyAndOropenid(String code ) throws Exception {
		
		Map<String,Object> map= new HashMap<>();
		if (StringUtils.isEmpty(code)) {
			throw new Exception("code 只不能  为空!!");
		}

		String respdatastring = restTemplete.getForEntity(
				"https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code",
				String.class).getBody();

		JSONObject jsonobjt = JSONObject.parseObject(respdatastring);

		if (jsonobjt.get("error") != "0") {

			throw new Exception("获取 sessionkey失败！！");
		}

		if (jsonobjt.containsKey("openid") && jsonobjt.containsKey("session_key")) {

			String openid = jsonobjt.get("openid").toString();
			String session_key = jsonobjt.get("session_key").toString();

			Log.info("获取  openid :" + openid + " ,sesionKey:" + session_key);
		
			map.put("openid", openid);
			map.put("session_key", session_key);
		}
		return   map;

	}

	@SuppressWarnings("unused")
	public  static String shal(String value) {
		if (value == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(value.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static String getFormattedText(byte[] bytes) {
		char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();

	}

}
