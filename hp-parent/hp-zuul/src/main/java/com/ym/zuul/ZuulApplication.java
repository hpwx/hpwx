package com.ym.zuul;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
* Description: 功能
* @author    : 郁如义
* @date      : 2018年9月2日
* Company    : 上海煜墨信息科技有限公司
* Copyright  : Copyright (c) 2018
* @version   :1.0
* Modified by 郁如义 at 2018年9月2日
*/
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ZuulApplication {

	private static final Logger LOG = LoggerFactory.getLogger(ZuulApplication.class);
	/**
	* Description: zuul网关启动类
	* @author    : 郁如义
	* @date      : 2018年9月2日
	* Company    : 上海煜墨信息科技有限公司
	* Copyright  : Copyright (c) 2018
	* @version   : 1.0
	* Modified by 郁如义 at 2018年9月2日
	*/
	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
		LOG.info("网关微服务启动成功");
	}
}
 