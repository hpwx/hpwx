package com.ym.rabbitmq.rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
* Description: MQ实时监听
* @author    : 郁如义
* @date      : 2018年9月2日
* Company    : 上海煜墨信息科技有限公司
* Copyright  : Copyright (c) 2018
* @version   :1.0
* Modified by 郁如义 at 2018年9月2日
 */
public class MQListener extends Thread{
	private static final Logger LOG = LoggerFactory.getLogger(MQListener.class);
	
	private String queueName = null;
	private ConnectionFactory factory = null;
	private String exchange = null;
	private IDealReceiveMsgService dealReceiveMsgService;
	
	public MQListener(String queueName, ConnectionFactory factory, String exChange,
			IDealReceiveMsgService dealReceiveMsgService) {
		this.queueName = queueName;
		this.factory = factory;
		this.exchange = exChange;
		this.dealReceiveMsgService = dealReceiveMsgService;
	}
	
	/**
	 * 消息监听
	 */
	@Override
	public void run() {
		LOG.info("启动{}线程",queueName);
		while(true) {
			Connection connection = null;
			Channel channel = null;
			Delivery delivery = null;
			String message = "";
			
			try {
				connection = factory.newConnection();
				channel = connection.createChannel();
				channel.exchangeDeclare(exchange, "direct",true);
				channel.basicQos(1);
				channel.queueBind(queueName, exchange, queueName);
				QueueingConsumer consumer = new QueueingConsumer(channel);
				channel.basicConsume(queueName, false,consumer);
				delivery = consumer.nextDelivery(10000);
				if(delivery != null) {
					message = new String(delivery.getBody(),"UTF-8");
					LOG.info("监听到的ip是：{}",factory.getHost());
					dealReceiveMsgService.dealMessage(message);
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				}
			}catch(Exception e) {
				LOG.error("消费队列消费消息异常：{}",message,e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					LOG.error("消息异常sleep",e);
				}
			}finally {
				try {
					channel.close();
					connection.close();
				} catch (IOException e) {
					LOG.error("IO异常了",e);
				} catch (TimeoutException e) {
					LOG.error("超时异常了",e);
				}
			}
		}
	}
}
