package com.ym.rabbitmq.rabbit;

/**
* Description: MQ监听到的数据处理接口
* @author    : 郁如义
* @date      : 2018年9月2日
* Company    : 上海煜墨信息科技有限公司
* Copyright  : Copyright (c) 2018
* @version   :1.0
* Modified by 郁如义 at 2018年9月2日
 */
public interface IDealReceiveMsgService {
	/**
	* Description: MQ消费者必须实现此接口
	* @author    : 郁如义
	* @date      : 2018年9月2日
	* @version   :1.0
	* Modified by 郁如义 at 2018年9月2日
	 */
	public void dealMessage(String msg) throws Exception;
}
