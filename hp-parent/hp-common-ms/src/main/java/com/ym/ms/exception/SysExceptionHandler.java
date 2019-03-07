package com.ym.ms.exception;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ym.ms.entity.Result;

@RestControllerAdvice
public class SysExceptionHandler {
	
	private Logger LOG = LoggerFactory.getLogger(getClass());

	//自定义系统异常
	@ExceptionHandler(SysException.class)
	public Result handlerSysException(SysException e) {
		return Result.error(e.getCode(), e.getMsg());
	}
	
//	@ExceptionHandler(DuplicateKeyException.class)
//	public Result handlerDuplicateKeyException(DuplicateKeyException e){
//		LOG.error(e.getMessage(),e);
//		return Result.error("数据库中已存在该条记录");
//	}
	
//	@ExceptionHandler(UncategorizedSQLException.class)
//	public Result handllerUncategorizedSQLException(UncategorizedSQLException e) {
//		logger.error(e.getMessage(),e);
//		return Result.error("数据库UncategorizedSQLException异常");
//	}
	
	@ExceptionHandler(SQLException.class)
	public Result handlerSQLException(SQLException e) {
		LOG.error(e.getMessage(),e);
		return Result.error("数据库SQL异常");
	}
	@ExceptionHandler(RuntimeException.class)
	public Result handlerRuntimeException(RuntimeException e) {
		LOG.error(e.getMessage(),e);
		return Result.error("运行时异常了");
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
    public Result MissingParamhandlerRuntimeException(RuntimeException e) {
        LOG.error(e.getMessage(),e);
        return Result.error("参数传递异常");
    }
    
	
	
	@ExceptionHandler(AuthorzationException.class)
	public Result handlerAuthorizationException(AuthorzationException e) {
		LOG.error(e.getMessage(),e);
		return Result.error("没有权限，请联系管理员");
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public Result handlerException(Exception e) {
		LOG.error(e.getMessage(),e);
		return Result.error();
	}
	
	
}
