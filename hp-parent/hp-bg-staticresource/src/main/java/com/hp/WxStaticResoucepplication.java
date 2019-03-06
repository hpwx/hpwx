package com.hp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableDiscoveryClient
//@EnableEurekaClient
@SpringBootApplication
public class WxStaticResoucepplication  extends WebMvcConfigurerAdapter{

	private final static   Logger  Log= LoggerFactory.getLogger(WxStaticResoucepplication.class);
	public static void main(String[] args) {
		
		SpringApplication.run(WxStaticResoucepplication.class, args);
		
		   Log.info("静态资源服务已启动.......");
	}
	  
	 @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/").setViewName("forward:/login.html");
	        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	        super.addViewControllers(registry);
	    }
	

 
 
 
}