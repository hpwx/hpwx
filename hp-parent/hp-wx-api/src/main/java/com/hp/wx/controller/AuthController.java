package com.hp.wx.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hp.wx.service.IwebChatService;
import com.ym.ms.entity.Result;

@RestController
public class AuthController {
@Autowired
	IwebChatService  webchageService;
	@GetMapping("onLogin")
	public Result onLogin(String code, String rawData) {
	   Map<String,Object> map= new HashMap<>();
		JSONObject  jsonobjet=	webchageService.getSessionKey(code ,rawData);
		map.put("openid", jsonobjet.get("openid")) ; 
		map.put("sessionkey", jsonobjet.get("skey")) ; 
		return    Result.ok(map);    
 	}
}
