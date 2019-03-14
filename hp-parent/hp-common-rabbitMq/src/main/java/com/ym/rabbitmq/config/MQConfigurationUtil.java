package com.ym.rabbitmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* Description: 封装MQ配置
* @author    : 郁如义
* @date      : 2018年9月2日
* Company    : 上海煜墨信息科技有限公司
* Copyright  : Copyright (c) 2018
* @version   :1.0
* Modified by 郁如义 at 2018年9月2日
 */
@Component
@ConfigurationProperties(prefix="mq.server")
public class MQConfigurationUtil {
	/**
	 * 用户名
	 */
	private String userName="ymroot";
	/**
	 * 密码
	 */
	private String passWord="Aa123456@";
	/**
	 * 交换器
	 */
	private String exchange="ym_exchange";
	/**
	 * ip列表，逗号分隔
	 */
	private String ips="39.104.181.176";
	/**
	 * 每个ip对应的端口，逗号分隔
	 */
	private String ports="5672";
	/**
	 * 队列名
	 */
	private String queue="ym_exchange_msg";
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getIps() {
		return ips;
	}
	public void setIps(String ips) {
		this.ips = ips;
	}
	public String getPorts() {
		return ports;
	}
	public void setPorts(String ports) {
		this.ports = ports;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
}
