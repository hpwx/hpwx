package com.ym.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
* Description: Eureka服务器启动类
* @author    : 郁如义
* @date      : 2018年9月2日
* Company    : 上海煜墨信息科技有限公司
* Copyright  : Copyright (c) 2018
* @version   :1.0
* Modified by 郁如义 at 2018年9月2日
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
	
	private static final Logger LOG = LoggerFactory.getLogger(EurekaApplication.class);
	
	/**
	* Description: Eureka服务端启动方法
	* @author    : 郁如义
	* @date      : 2018年9月2日
	* @version   : 1.0
	* Modified by 郁如义 at 2018年9月2日
	 */
	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class,args);
		LOG.info("Eureka服务器启动成功");
	}
}
