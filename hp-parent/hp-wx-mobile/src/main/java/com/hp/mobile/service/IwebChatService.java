package com.hp.mobile.service;

import com.alibaba.fastjson.JSONObject;

public interface IwebChatService {

	  JSONObject  getSessionKey(String code,String rawData); 
}
