package com.ym.ms.logAspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)
public class SysLogAspect {
	private static final Logger LOG = LoggerFactory.getLogger(SysLogAspect.class);
	
	@Pointcut("@annotation(com.ym.ms.logAspect.SysLog)")
	public void logPointCut() {
		
	}
	
	@Before("logPointCut()")
	public void saveSysLog(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		
//		SysLogEntity sysLog = new SysLogEntity();
//		SysLog syslog = method.getAnnotation(SysLog.class);
//		if(syslog != null) {
//			//注解上的描述
//			sysLog.setOperation(syslog.value());
//		}
//		
//		//请求的方法名
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = signature.getName();
//		sysLog.setMethod(className + "." + methodName + "()");
//		
//		//请求的参数
//		Object[] args = joinPoint.getArgs();
//		String params = new Gson().toJson(args[0]);
//		sysLog.setParams(params);
//		
//		//获取request
//		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//		//设置IP地址
//		sysLog.setIp(IPUtils.getIpAddr(request));
//		
//		//用户名
//		String userName = SecurityUtils.getSubject().getPrincipal().getUserName();
//	    sysLog.setUserName(userName);
//		
//		sysLog.setCreateDate(new Date());
		
		//通过kafka保存日志到ELK
	}
}
