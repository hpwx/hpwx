package com.ym.ms.exception;

/**
 * Description: 返回码和返回信息定义
 * 
 * @author : 郁如义
 * @date : 2018年9月9日 Company : 上海煜墨信息科技有限公司 Copyright : Copyright (c) 2018
 * @version : 1.0 Modified by 郁如义 at 2018年9月9日
 */
public enum CodeMsgEnum {
  // 成功标识
  SUCCESS("0000", "成功"), ERROR("0500", "网路异常");


  private String code;
  private String msg;

  private CodeMsgEnum(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

}
