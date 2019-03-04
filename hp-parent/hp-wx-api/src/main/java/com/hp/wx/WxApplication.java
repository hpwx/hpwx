package com.hp.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class WxApplication {
	private final static   Logger  Log= LoggerFactory.getLogger(WxApplication.class);
	public static void main(String[] args) {
		
		SpringApplication.run(WxApplication.class, args);
		
		   Log.info("微信前台业务服务已启动.......");
	}
	 @Bean
	 RestTemplate   getRestTemplate() { 
		 return new RestTemplate();
	 }
}
