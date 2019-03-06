package com.hp.mobile.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hp.mobile.utils.SecrutiyUtil;

 

 @RestController
@RequestMapping("/hpwxapi")
public class ValiteWxToken {

	@Value(value="${wx.token}")
    private     String TOKEN;
	
	Logger LOG=LoggerFactory.getLogger(ValiteWxToken.class);
	
	/*
	 * 验证微信token 
	 */
	
	@RequestMapping(value="/tokenvalite",method=RequestMethod.GET)
	public String initWeiXin(HttpServletRequest req , HttpServletResponse resp) throws IOException {
		/*
	  	signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		timestamp	时间戳
		nonce	随机数
		echostr	随机字符串
		 */
		LOG.info("TOKEN为:{}",TOKEN);
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		LOG.info("====》接收到的参数singature为:{}",signature);
		LOG.info("====》接收到的参数timestamp为:{}",timestamp);
		LOG.info("====》接收到的参数nonce为:{}",nonce);
		LOG.info("====》接收到的参数echostr为:{}",echostr);
		
	   boolean  issucess=	SecrutiyUtil.checkSignature(signature, timestamp, nonce);
	   
	   if (!issucess) {
		   
		   LOG.info("==============》token 验证失败！！");
			return null;
	   }
	   LOG.info("==============》token 验证成功！！ " );
	   return  echostr;
		 
	}
	
	
	
	
}
