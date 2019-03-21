package com.hp.modules.sys.controller;

import com.hp.common.utils.EncryptUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller公共组件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月9日 下午9:42:26
 */
@Component
public abstract class AbstractController {

	@Autowired
	HttpServletRequest request; //这里可以获取到request

	@Value("${userKey}")
	private String userKey;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
//		return getUser().getUserId();
		Cookie[] cookies = request.getCookies();
		String userId = null;
		for (Cookie cookie:cookies) {
			if(cookie.getName().equals("userInfo")){
				userId = cookie.getValue();
			}
		}
		String user = EncryptUtil.getInstance().DESdecode(userId, userKey);

		return Long.valueOf(user);
	}
}
