package com.ym.ms.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
* Description: Controller层 父类
* @author    : 郁如义
* @date      : 2018年9月2日
* Company    : 上海煜墨信息科技有限公司
* Copyright  : Copyright (c) 2018
* @version   :1.0
* Modified by 郁如义 at 2018年9月2日
 */
public class BaseController {
	protected Logger LOG = LoggerFactory.getLogger(getClass());
	
	
//	protected SysUserEntity getUser() {
//		try {
//			return SecurityUtils.getSubject().getPrincipal();
//		}catch(Exception e) {
//			LOG.error("shiro error {}" ,e.getMessage(),e);
//		}
//		return null;
//	}
	
	/**
	* Description: 时间类型转换器
	* @author    : 郁如义
	* @date      : 2018年9月2日
	* @version   :1.0
	* Modified by 郁如义 at 2018年9月2日
	 */
	@InitBinder
	public void initBinder(WebDataBinder bind) {
		DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
		dateFormat.setLenient(true);
		bind.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
	}
}
