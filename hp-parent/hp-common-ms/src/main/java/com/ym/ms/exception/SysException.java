package com.ym.ms.exception;
public class SysException extends RuntimeException{
	private String code = "0000500";
	private String msg = "内部错误";
	
	public SysException() {
		super();
	}
	
	public SysException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public SysException(String msg,Throwable e) {
		super(msg,e);
		this.msg = msg;
	}
	
	public SysException(String code,String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}
	
	public SysException(String code,String msg,Throwable e) {
		super(msg,e);
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
