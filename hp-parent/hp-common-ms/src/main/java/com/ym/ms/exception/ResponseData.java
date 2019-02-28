package com.ym.ms.exception;

/**
 * 返回数据格式
 */
public class ResponseData {

	private boolean flag;// 代表当前代码是否完全执行

	private boolean check;// 代表验证水杯是是否是可绑定的

	private String msg = "";// 返回到APP的信息

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
