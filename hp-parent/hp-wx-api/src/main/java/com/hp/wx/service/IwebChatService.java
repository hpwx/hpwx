package com.hp.wx.service;

import com.alibaba.fastjson.JSONObject;

public interface IwebChatService {

	  JSONObject  getSessionKey(String code,String rawData); 
}
