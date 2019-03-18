package com.hp.mobile.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
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
import com.hp.mobile.service.IwebChatService;
import com.hp.mobile.utils.WxApiUtils;
import com.ym.ms.exception.CodeMsgEnum;
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
	public JSONObject getSessionKey(String code  ) {

		JSONObject retobjson = new JSONObject();

		// TODO Auto-generated method stub
		LOG.info("获取前台请求的Code：" + code);

		Map<String, Object> map = null;
		try {
			map = WxApiUtils.getSessionKeyAndOropenid(code);
			
			 if (map != null && !StringUtils.isEmpty(map.get("openid")) && !StringUtils.isEmpty(map.get("session_key"))) {

	            String openid = map.get("openid").toString();
	            String session_key = map.get("session_key").toString();
	            retobjson.put("openid",map.get("openid").toString());
                retobjson.put("sessionkey", session_key)   ;
                
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
  @Override
  public JSONObject saveUserInfo(String openid  ,String userinfo )   {
    JSONObject retobjson = new JSONObject();
//LOG.info("json字符串："+JSON.toJSONString(userinfo));
    
    JSONObject      rawDataJson =  JSONObject.parseObject(userinfo);
    String nickName ="" ;
      byte[] textByte;
      try {
        textByte = rawDataJson.getString("nickName").getBytes("UTF-8");
        nickName = Base64.getEncoder().encodeToString(textByte);
      } catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
  //编码
   // String nickName =     rawDataJson.getString("nickName");
    String avatarUrl = rawDataJson.getString("avatarUrl");
    Byte gender =  Byte.valueOf(rawDataJson.get("gender").toString())  ;
    String city = rawDataJson.getString("city");
    UserInfo  user = userInfoMapper.selectUserByOpenId(openid)  ;
    if (user==null) {
      user=new  UserInfo();
      user.setOpenId((openid));
      user.setHeadIcon(avatarUrl);
      user.setSex(gender);
      user.setCity(city);
      user.setNick(nickName);
      user.setCreateTime(new Date());
     userInfoMapper.insert(user);
    }else {
      
      user.setNick(nickName);
      user.setCity(city);
      user.setOpenId(openid);
      user.setCreateTime(new Date());
      user.setHeadIcon(avatarUrl);
      user.setSex(gender);
      userInfoMapper.updatebyOpenid(user) ;
    }
       String nkname= new  String (Base64.getDecoder().decode(user.getNick()));
       user.setNick(nkname);
    // TODO Auto-generated method stub
    retobjson.put( "userinfo",  user);
    
    return retobjson;
  }
  @Override
  public JSONObject getUserInfo(String openid) {
     
    JSONObject ret = new JSONObject();
   UserInfo userinfo=   userInfoMapper.selectUserByOpenId(openid);
 
   String nkname= new  String (Base64.getDecoder().decode(userinfo.getNick()));
      
   userinfo.setNick(nkname);
   ret.put("userinfo",  userinfo);
   
   
    return ret;
  }

	

}
