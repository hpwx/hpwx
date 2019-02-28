package com.ym.rabbitmq.rabbit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.ym.rabbitmq.config.MQConfigurationUtil;

/**
* Description: MQ生产者
* @author    : 郁如义
* @date      : 2018年9月2日
* Company    : 上海煜墨信息科技有限公司
* Copyright  : Copyright (c) 2018
* @version   :1.0
* Modified by 郁如义 at 2018年9月2日
 */
@Component
public class MQProducter {
	private static final Logger LOG = LoggerFactory.getLogger(MQProducter.class);
	
	@Autowired
	private MQConfigurationUtil mqUtil;
	
	private String username;
	private String password;
	private String exchange;
	private List<Map<String,Object>> configList = null;
	
	@PostConstruct
	public void init() {
		username = mqUtil.getUserName();
		password = mqUtil.getPassWord();
		exchange = mqUtil.getExchange();
		String ips = mqUtil.getIps();
		String ports = mqUtil.getPorts();
		if(!StringUtils.isEmpty(ips)) {
			String[] ipArray = ips.split(",");
			String[] portArray = ports.split(",");
			configList = new ArrayList<Map<String,Object>>();
			for(int i = 0; i < ipArray.length;i++) {
				Map<String,Object> innerMap = new HashMap();
				innerMap.put("ip", ipArray[i]);
				innerMap.put("port", portArray[i]);
				configList.add(innerMap);
			}
		}
	}
	
	/**
	* Description: MQ发送消息
	* @author    : 郁如义
	* @date      : 2018年9月2日
	* @version   :1.0
	* Modified by 郁如义 at 2018年9月2日
	 */
	public boolean sendMessage(String queue,String message) {
		if(configList == null || configList.size() < 0) {
			return false;
		}
		
		int mqSize = configList.size();
		int random = (int)(Math.random() * mqSize);
		
		for(int i = 0; i < mqSize;i++) {
			Map<String,Object> map = configList.get((random + 1) % mqSize);
			
			ConnectionFactory factory;
			
			Channel channel = null;
			
			Connection connection = null;
			
			String ip = (String) map.get("ip");
			try {
				factory = new ConnectionFactory();
				factory.setHost(ip);
				factory.setPort(Integer.valueOf((String)map.get("port")));
				factory.setUsername(username);
				factory.setPassword(password);
				connection = factory.newConnection();
				channel = connection.createChannel();
				channel.exchangeDeclare(exchange, "direct", true);
				channel.basicQos(1);
				channel.queueBind(queue, exchange, queue);
				byte[] buffer = message.getBytes();
				channel.basicPublish(exchange, queue,MessageProperties.PERSISTENT_TEXT_PLAIN, buffer);
				LOG.info(ip + ": 发送成功");
				return true;
			}catch(Exception e) {
				LOG.info(ip + ": 发送数据异常了");
				if(channel != null) {
					try {
						channel.close();
						connection.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (TimeoutException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		return false;
	}
}
