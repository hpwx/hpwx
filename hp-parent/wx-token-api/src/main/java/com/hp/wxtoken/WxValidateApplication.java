package com.hp.wxtoken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class WxValidateApplication {

	
	private final static   Logger  Log= LoggerFactory.getLogger(WxValidateApplication.class);
	public static void main(String[] args) {
		
		SpringApplication.run(WxValidateApplication.class, args);
		   Log.info("验证 token服务已启动.......");
	}
	

}
