package com.ym.utils.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public static Object getBeanByName(String name) {
		return applicationContext.getBean(name);
	}
	
	public static <T> T getBeanByType(String name,Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}
	
	public static Class<? extends Object> getType(String name){
		return applicationContext.getType(name);
	}

}
