package com.hp.mobile.controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hp.mobile.service.IwebChatService;
import com.hp.mobile.service.impl.userSericeimpl;
import com.ym.ms.entity.Result;

/**
 * 认证 用户登录
 * @author gq
 *
 */

@RestController
@RequestMapping("/auth")
public class AuthController {
     
  private final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    @Autowired
	IwebChatService  webchageService;
    
	@GetMapping("/onLogin")
	  public Result onLogin( @RequestParam  Map<String,Object> req) {
	    LOG.info("获取 code:"+  req.get("code"));
	   
	  //String code, String rawData
	   Map<String,Object> map= new HashMap<>();
		JSONObject  jsonobjet=	webchageService.getSessionKey(req.get("code").toString()   );
		map.put("openid", jsonobjet.get("openid")) ; 
		map.put("sessionkey", jsonobjet.get("sessionkey")); 
		return  Result.ok(map);    
 	}
	
	
	@GetMapping("/saveUserInfo")
    public Result saveUserInfo(@RequestParam  Map<String,Object> req) {
      LOG.info("获取 openid:"+  req.get("openid") );
      LOG.info("获取 rawData:"+  req.get("rawData").toString());
    //String code, String rawData
//     Map<String,Object> map= new HashMap<>();
      JSONObject  jsonobjet=  webchageService.saveUserInfo(req.get("openid").toString() ,   req.get("rawData").toString()  ) ;
 
      return  Result.ok(jsonobjet);    
  }
	
	@GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam  Map<String,Object> req) {
      LOG.info("获取 openid:"+  req.get("openid") );
     
    //String code, String rawData
//     Map<String,Object> map= new HashMap<>();
      JSONObject  jsonobjet=  webchageService.getUserInfo(req.get("openid").toString()) ;
      return  Result.ok(jsonobjet);    
  }
	
}
