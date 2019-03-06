package com.hp.wx.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.hp.redis.utils.RedisUtils;
import com.hp.wx.entity.UserInfo;
import com.hp.wx.mapper.UserInfoMapper;
import com.hp.wx.service.IwebChatService;
import com.hp.wx.utils.WxApiUtils;
import com.ym.ms.exception.SysException;

@Service
public class WebChatServiceImpl implements IwebChatService {

 
	@Value("${auth.wechat.appId}")
	private String appId;
	@Value("${auth.wechat.secret}")
	private String secret;
	@Autowired
	private RestTemplate restTemplate;
	private final Logger LOG = LoggerFactory.getLogger(userSericeimpl.class);

	@Autowired
	private UserInfoMapper  userInfoMapper;
	@Override
	public JSONObject getSessionKey(String code, String rawData) {

		JSONObject jsonobject = new JSONObject();

		// TODO Auto-generated method stub
		LOG.info("获取前台请求的Code：" + code);

		Map<String, Object> map = null;
		try {
			map = WxApiUtils.getSessionKeyAndOropenid(code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 判断是否为空值
		if (map != null && !StringUtils.isEmpty(map.get("openid")) && !StringUtils.isEmpty(map.get("session_key"))) {

			String openid = map.get("openid").toString();
			String session_key = map.get("session_key").toString();

			// 根据 openid 查找 数据库 是否已经存在 ,如果已存在 则 更新数据库 。
			UserInfo userinfo = getUserInfoByOpenId(openid);

			if (userinfo == null) {
				JSONObject rawDataJson = JSONObject.parseObject(rawData);
				String nickName = rawDataJson.getString("nickName");
				String avatarUrl = rawDataJson.getString("avatarUrl");
				Byte gender =  Byte.valueOf(rawDataJson.get("gender").toString())  ;
				String city = rawDataJson.getString("city");
				String country = rawDataJson.getString("country");
				// 插入用户信息 ;
		 
				userinfo=new  UserInfo();
				userinfo.setHeadIcon(avatarUrl);
				userinfo.setSex(gender);
				userinfo.setCity(city);
				userinfo.setCreateTime(new Date());
				userinfo.setNick(nickName);
			  int resut=	userInfoMapper.insert(userinfo);
			  if (resut==0) {
				  
				  LOG.info(" 调用方法 :getSessionKey（）===== 用户信息数据写入失败！！");
			  }
			}
			String skey = WxApiUtils.shal(openid + session_key);
			// 记录到 缓存中 。。
			UUID.randomUUID().toString().replace("-", "");
			RedisUtils redisutils = new RedisUtils();
			if (!StringUtils.isEmpty(redisutils.get(skey))) {
				redisutils.delete(skey);
			}
			JSONObject sessionObj = new JSONObject();
			sessionObj.put("openId", openid);
			sessionObj.put("sessionkey", session_key);
			redisutils.set(skey, sessionObj.toJSONString());

			JSONObject retuvalue = new JSONObject();

			retuvalue.put("skey", skey);
			retuvalue.put("openid", openid);
			retuvalue.put("userinfo", userinfo);
			return retuvalue;
		} else {
			LOG.info("获取 微信 sessionkey失败 ！！");

		}

		return null;

	}

	private UserInfo getUserInfoByOpenId(String openid) {
		// TODO Auto-generated method stub
		return null;
	}

}
