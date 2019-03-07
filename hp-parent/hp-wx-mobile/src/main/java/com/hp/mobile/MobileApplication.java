package com.hp.mobile;

import javax.servlet.annotation.WebInitParam;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import com.ym.ms.exception.SysExceptionHandler;

 

 

@SpringBootApplication
@Import({ SysExceptionHandler.class})
 
public class MobileApplication {
	private final static   Logger  Log= LoggerFactory.getLogger(MobileApplication.class);
public static void main(String[] args) {
		
		SpringApplication.run(MobileApplication.class, args);
		   Log.info("微信小程序服务已启动.......");
	}
  
@Bean
  public  RestTemplate  getRestTemplete() {
	return new RestTemplate();
}
}
