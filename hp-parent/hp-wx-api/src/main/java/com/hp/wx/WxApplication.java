package com.hp.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@SpringBootApplication
public class WxApplication {
	private final static   Logger  Log= LoggerFactory.getLogger(WxApplication.class);
	public static void main(String[] args) {
		
		SpringApplication.run(WxApplication.class, args);
		
		   Log.info("微信前台服务已启动.......");
	}
	
}
