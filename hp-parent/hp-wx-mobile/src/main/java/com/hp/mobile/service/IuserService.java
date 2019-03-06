package com.hp.mobile.service;

import com.hp.mobile.entity.UserInfo;

public interface IuserService {
 
   UserInfo  getUserByOpenId(String openid) ;
   
}
