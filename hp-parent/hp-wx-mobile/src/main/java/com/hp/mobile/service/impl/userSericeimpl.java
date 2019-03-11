package com.hp.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hp.mobile.entity.UserInfo;
import com.hp.mobile.mapper.UserInfoMapper;
import com.hp.mobile.service.IuserService;
@Transactional
@Service
public class userSericeimpl implements  IuserService{

  
  @Autowired
  private UserInfoMapper  userInfoMapper;
  @Override
  public UserInfo getUserByOpenId(String openid) {
    // TODO Auto-generated method stub
    
     return   userInfoMapper.selectUserByOpenId(openid);
   
  }

	

	
	
}
