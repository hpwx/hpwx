package com.hp.wx.utils;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
 
import com.hp.redis.utils.RedisUtils;

  

public class WxService {

	
	static final  Logger LOG= LoggerFactory.getLogger(WxService.class);
	public JSONObject onlgin(String code, String rawData, String signature, String encryptedData, String iv)
			throws Exception {

		Map<String, Object> map = WxApiUtils.getSessionKeyAndOropenid(code);
		if (map != null) {

			String openid = map.get("openid").toString();
			String session_key = map.get("session_key").toString();

			// 根据 openid 查找 数据库 是否已经存在 ,如果已存在 则 更新数据库 。
			Object userinfo = getUserInfoByOpenId(openid);

			if (userinfo == null) {
				JSONObject rawDataJson = JSONObject.parseObject(rawData);
				String nickName = rawDataJson.getString("nickName");
				String avatarUrl = rawDataJson.getString("avatarUrl");
				String gender = rawDataJson.getString("gender");
				String city = rawDataJson.getString("city");
				String country = rawDataJson.getString("country");
				// 插入用户信息 ;
			}

			String skey = WxApiUtils.shal(openid + session_key);
			// 记录到 缓存中 。。
			UUID.randomUUID().toString().replace("-", "");
			RedisUtils redisutils = new RedisUtils();
			if (! StringUtils.isEmpty(redisutils.get(skey))) {
				redisutils.delete(skey);
			}
			JSONObject sessionObj = new JSONObject();
			sessionObj.put("openId", openid);
			sessionObj.put("sessionkey", session_key);
			redisutils.set(skey, sessionObj.toJSONString());

			JSONObject retuvalue = new JSONObject();

			retuvalue.put("skey", skey);
			retuvalue.put("openid", openid);
			retuvalue.put("userinfo", "");
			return retuvalue;
		}else {
			LOG.info("获取 微信 sessionkey 失败 ！！");
			 
		}
		

		return null;
	}

	
	
/**
 *  解密数据 。。。。。
 * @param code
 * @param skey
 * @param encryptedData
 * @param iv
 * @return
 */
	public JSONObject decrptUserInfo(String code, String skey, String encryptedData, String iv) {

		RedisUtils redis = new RedisUtils();
		JSONObject skeyinfo = JSONObject.parseObject(redis.get("skey"));

		String session_key = skeyinfo.get("sessionkey").toString();
		String result = AESUtils.decrypt(session_key, encryptedData, iv);
 
		JSONObject decryptuserinfo = JSONObject.parseObject(result);

		if (null != decryptuserinfo) {

			return decryptuserinfo;
		}
		return null;
	}

	/**
	 * 查找数据库 是否用户已存在 ！！
	 * 
	 * @param openid
	 * @return
	 */
	private Object getUserInfoByOpenId(String openid) {

		return null;
	}
}
