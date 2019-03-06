package com.hp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
 
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hp.datasources.DynamicDataSourceConfig;

 
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@Import({DynamicDataSourceConfig.class})
public class BgApplication  extends SpringBootServletInitializer {

	
	private     final static   Logger  Log=  LoggerFactory.getLogger(BgApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BgApplication.class, args);
		Log.info("微信后台服务已启动......");
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BgApplication.class);
	}
	
	 
}
