package com.ym.ms.filter;

import org.springframework.util.StringUtils;

import com.ym.ms.exception.SysException;

public class SQLFilter {
	//SQL注入过滤
	public static String sqlInject(String str) {
		if(StringUtils.isEmpty(str)) {
			return null;
		}
		
		str = StringUtils.replace(str, "'", "");
		str = StringUtils.replace(str, "\"", "");
		str = StringUtils.replace(str, ";", "");
		str = StringUtils.replace(str, "\\", "");
		
		//转换成小写
		str = str.toLowerCase();
		
		//非法字符
		String[] keywords = {"master","truncate","insert",
				"select","delete","update","declare","alert","drop"};
		
		//判断是否包含非法字符
		for(String keyword : keywords) {
			if(str.indexOf(keyword) != -1) {
				throw new SysException("包含非法字符");
			}
		}
		
		return str;
	}
}
