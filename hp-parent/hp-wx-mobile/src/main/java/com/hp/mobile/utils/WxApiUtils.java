package com.hp.mobile.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.hp.redis.utils.RedisUtils;
import com.ym.ms.exception.CodeMsgEnum;
import com.ym.ms.exception.SysException;

 
@Component
public class WxApiUtils {

	@Autowired

	private  static    RestTemplate restTemplete;
	 
	@Autowired

	private   static   RedisUtils redisUtils;
	@Value("${wx.appId}")
	private    String appId;
	@Value("${wx.secret}")
	private    String secret;

	private static final Logger Log = LoggerFactory.getLogger(WxApiUtils.class);

	
	
	 @PostConstruct
     public void beforeInit() {
      restTemplete = new RestTemplate();
      redisUtils= new RedisUtils();
     }
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
				"https://api.weixin.qq.com/sns/jscode2session?appid="+Config.appId+"&secret="+Config.secret+"&js_code="+code+"&grant_type=authorization_code",
				String.class).getBody();

		JSONObject jsonobjt = JSONObject.parseObject(respdatastring);
              Log.info("获取 sessionkey 接口返回："+jsonobjt.toJSONString());
	 
            
		if (jsonobjt.containsKey("openid") && jsonobjt.containsKey("session_key")) {

			String openid = jsonobjt.get("openid").toString();
			String session_key = jsonobjt.get("session_key").toString();

			Log.info("获取  openid :" + openid + " ,sesionKey:" + session_key);
		
			map.put("openid", openid);
			map.put("session_key", session_key);
		}
		return   map;

	}

	
	/**
	 * 
	 * @Author yuruyi
	 * @Description   获取acesstoken  
	 * @Date   2019年3月10日
	 * @Param  
	 * @return  
	 *
	 */
	public  static  String getAcessToken() {
	  
//	  
//	  RedisUtils redis = new RedisUtils();
//	   if ( redis.get("accesstoken")!=null&& redis.get("accesstoken").length()!=0 ) {
//	     
//	     return redis.get("accesstoken");
//	   }
	  
	  String requestUrl =
          "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Config.appId
              + "&secret=" + Config.appId + "";
      String respdata = restTemplete.getForEntity(requestUrl, String.class).getBody();

      JSONObject jsonObject = JSONObject.parseObject(respdata);

      Log.info(" 调用 获取 acesstoken 接口 返回结果：" + jsonObject.toJSONString());
       
//        redis.delete("accesstoken");
//        // 存储 acesstoken 到redis  有效时间 为  30 分钟 
//        redis.set("accesstoken", jsonObject.getString("access_token"), 1*60*30);
  
        Log.info("将 acesstoken  存储到 redis 中 ， accesstoken：" + jsonObject.getString("accesstoken"));
      
	  
	  return null;
	}
	
	
	
	public static  String getJsTiket(String acesstoken) {
	  
	  String requestUrl ="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+acesstoken+"&type=jsapi";
         
      String respdata = restTemplete.getForEntity(requestUrl, String.class).getBody();

      JSONObject jsonObject = JSONObject.parseObject(respdata);
      
    return  jsonObject.get("ticket").toString();
      
	  
	 
	}
	 public  static  Map<String, String>   getSignatureData(String jsapi_ticket, String url) {
	   Map<String, String> ret = new HashMap<String, String>();
	      String  nonce_str = UUID.randomUUID().toString();
	      String timestamp = Long.toString(System.currentTimeMillis() / 1000);//时间戳
	        
	    String   data = "jsapi_ticket=" + jsapi_ticket +
              "&noncestr=" + nonce_str +
              "×tamp=" + timestamp +
              "&url=" + url;
 
	      String signature=     ret.put("url", data);
	        ret.put("jsapi_ticket", jsapi_ticket);
	        ret.put("nonceStr", nonce_str);
	        ret.put("timestamp", timestamp);
	        ret.put("signature", signature);
	        return ret;
	 }

}
