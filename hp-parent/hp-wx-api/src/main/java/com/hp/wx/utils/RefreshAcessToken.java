package com.hp.wx.utils;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.hp.redis.utils.RedisUtils;

 


/**
 *   定时 任务  在启动类中 天添加       @EnableScheduling
 * @author Administrator
 *
 */

@Component
public class RefreshAcessToken {
	RestTemplate  RestTemplate;
	@Value("${Appid}")
	private String appid;
	@Value("${AppSecret}")
	private String appsecret;
	
	private     org.slf4j.Logger Logger= LoggerFactory.getLogger(RefreshAcessToken.class);
	
	/*
	 *  每隔 90 分钟刷新下  acesstoken 
	 */
	
	@Scheduled(cron="0 */90 * * * ? ")
	 public  void   getAcessToken() {
		  try {
	            String appid = "填上你公众号的appid";
	            String appsecret = "填上你公众号的appsecret";
	            String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret+"";
	            requestUrl = requestUrl.replace("APPID",appid).replace("APPSECRET",appsecret);
	           
	          String  respdata=  RestTemplate.getForEntity(requestUrl, String.class).getBody();
	            
	          JSONObject jsonObject=   JSONObject.parseObject(respdata);
	             
	          if(jsonObject.getString("access_token")!=null){
	               RedisUtils redis= new RedisUtils();
	               redis.delete("access_token");
	               redis.set("access_token", jsonObject.getString("access_token"));
	                 
	            }
	            else{
	            	Logger.info("定时刷新access_token失败，微信返回的信息是"+jsonObject.toJSONString());
	            }
	        }
	        catch (Exception e){
	        	Logger.info("更新access_token的过程当中发生了异常，异常的信息是"+e.getMessage());
	        }
	 }

@Bean
RestTemplate    getRestTemplete(){
	
	return  new  RestTemplate();
	
}

}
