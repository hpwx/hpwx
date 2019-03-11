package com.hp.mobile.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

 
 
    public   static   String appId;
    public   static  String secret;
   @Value("${wx.appId}")
   public void setAppId(String appId) {
     this.appId = appId;
   }
   
   @Value("${wx.secret}")
   public void setSecret(String secret) {
     this.secret = secret;
   }
}
