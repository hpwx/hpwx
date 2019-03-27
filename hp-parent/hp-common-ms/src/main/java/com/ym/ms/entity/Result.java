package com.ym.ms.entity;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ym.ms.exception.CodeMsgEnum;

/**
 * Description: 接口返回值封装
 * 
 * @author : 郁如义
 * @date : 2018年9月2日 Company : 上海煜墨信息科技有限公司 Copyright : Copyright (c) 2018
 * @version :1.0 Modified by 郁如义 at 2018年9月2日
 */
public class Result extends HashMap<String, Object> {
  private static final Logger LOG = LoggerFactory.getLogger(Result.class);

  private Result() {
    put("code", CodeMsgEnum.SUCCESS.getCode());
    put("msg", CodeMsgEnum.SUCCESS.getMsg());
  }

  public static Result error() {
    return error(CodeMsgEnum.ERROR.getMsg());
  }

  public static Result error(String msg) {
    return error(CodeMsgEnum.ERROR.getCode(), msg);
  }

  public static Result error(String code, String msg) {
    Result r = new Result();
    r.put("code", code);
    r.put("msg", msg);
    LOG.info("返回值是：{}",
        JSON.toJSONString(r, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue));
    return r;
  }

  public static Result ok() {
    Result r = new Result();
    LOG.info("返回值是：{}",
        JSON.toJSONString(r, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue));
    return r;
  }


  public static Result keyValueOk(String key, Object t) {
    Result r = new Result();
    r.put(key, t);
    LOG.info("返回值是：{}",
        JSON.toJSONString(r, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue));
    return r;
  }

  public static Result ok(String msg) {
    Result r = new Result();
    r.put("msg", msg);
    LOG.info("返回值是：{}",
        JSON.toJSONString(r, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue));
    return r;
  }

  public static Result ok(Map<String, Object> map) {
    Result r = new Result();
    r.put("data", map);
    LOG.info("返回值是：{}",
        JSON.toJSONString(r, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue));
    return r;
  }

  public static Result ok(Object t) {
    Result r = new Result();
    r.put("data", t);
    LOG.info("返回值是：{}",
        JSON.toJSONString(r, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue));
    return r;
  }

  @Override
  public Result put(String key, Object value) {
    super.put(key, value);
    return this;
  }
}
