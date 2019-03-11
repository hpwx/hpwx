package com.hp.mobile.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.hp.mobile.entity.UserInfo;
import com.hp.mobile.mapper.UserInfoMapper;
import com.hp.mobile.service.IuserService;
import com.hp.mobile.service.IwebChatService;
import com.hp.mobile.utils.SecrutiyUtil;
import com.hp.mobile.utils.WxApiUtils;
import com.hp.redis.utils.RedisUtils;
@Transactional
@Service
public class WebChatServiceImpl implements IwebChatService {

 
	@Value("${wx.appId}")
	private String appId;
	@Value("${wx.secret}")
	private String secret;
	@Autowired
	private RestTemplate restTemplate;
	
	
	private final Logger LOG = LoggerFactory.getLogger(userSericeimpl.class);

	@Autowired
	private UserInfoMapper  userInfoMapper;
	@Override
	public JSONObject getSessionKey(String code, String rawData) {

		JSONObject retobjson = new JSONObject();

		// TODO Auto-generated method stub
		LOG.info("获取前台请求的Code：" + code);

		Map<String, Object> map = null;
		try {
			map = WxApiUtils.getSessionKeyAndOropenid(code);
			
			 if (map != null && !StringUtils.isEmpty(map.get("openid")) && !StringUtils.isEmpty(map.get("session_key"))) {

	            String openid = map.get("openid").toString();
	            String session_key = map.get("session_key").toString();
	            JSONObject      rawDataJson =  JSONObject.parseObject(rawData);
	            retobjson.put("userinfo", rawDataJson.toJSONString());
	            retobjson.put("openid",map.get("openid").toString());
	             retobjson.put("sessionkey", session_key) ;
	            String nickName = rawDataJson.getString("nickName");
                String avatarUrl = rawDataJson.getString("avatarUrl");
                Byte gender =  Byte.valueOf(rawDataJson.get("gender").toString())  ;
                String city = rawDataJson.getString("city");

	            // 根据 openid 查找 数据库 是否已经存在 ,如果已存在 则 更新数据库 。
	            UserInfo userinfo = userInfoMapper.selectUserByOpenId(openid)  ;
	            if (userinfo == null) {
	                // 插入用户信息 ;
	                userinfo=new  UserInfo();
	                userinfo.setOpenId((openid));
	                userinfo.setHeadIcon(avatarUrl);
	                userinfo.setSex(gender);
	                userinfo.setCity(city);
	                userinfo.setNick(nickName);
	                userinfo.setCreateTime(new Date());
	               userInfoMapper.insert(userinfo);
	              
	            }else {
	              userinfo.setNick(nickName);
	              userinfo.setCity(city);
	              userinfo.setOpenId(openid);
	              userinfo.setCreateTime(new Date());
	              userinfo.setHeadIcon(avatarUrl);
	              userinfo.setSex(gender);
	              userInfoMapper.updatebyOpenid(userinfo) ;
	            }
	            
			 } else {
	            LOG.info("获取 微信 sessionkey失败 ！！");
	        }
		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		LOG.info(e.getMessage());
		}
		// 判断是否为空值
		return retobjson;
	}

	

}
