package com.ym.ms.entity;

import java.io.Serializable;

import com.ym.ms.filter.SQLFilter;

/**
* Description: 操作数据库时，传递的参数需要排序，则继承此类
* @author    : 郁如义
* @date      : 2018年9月2日
* Company    : 上海煜墨信息科技有限公司
* Copyright  : Copyright (c) 2018
* @version   :1.0
* Modified by 郁如义 at 2018年9月2日
 */
public class BaseEntity implements Serializable{

	//排序字段
	protected String orderField;
	//排序标识
	protected String order;
	
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = SQLFilter.sqlInject(orderField);
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = SQLFilter.sqlInject(order);
	}
	
	
}
