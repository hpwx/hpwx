package com.hp.mobile.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hp.mobile.entity.UserAnswer;
import com.hp.mobile.service.IQuestionNaire;
import com.hp.mobile.utils.WxApiUtils;
import com.ym.ms.entity.Result;

@RestController
@RequestMapping("/questionnaire")


public class QuestionNaireController {


  @Autowired
  private IQuestionNaire questionNaire;

  /**
   * 
   * @Author yuruyi
   * @Description 查询用户的问卷
   * @Date 2019年3月7日
   * @Param
   * @return
   *
   */
  @RequestMapping("/getQuestionNaireAndSubjectByOpenId")
  public Result getQuestionNaireByOpenId(@RequestParam("openid") String openid) {

    Map<String, Object> map = questionNaire.getUserQustionNarie(openid);
    return Result.ok(map);
  }

  
  
  /**
   * 
   * @Author yuruyi
   * @Description    用戶提交答卷
   * @Date   2019年3月11日
   * @Param  
   * @return  
   *
   */
  @RequestMapping("/commitQustionNaire")
  public Result commitQustionNaire(@RequestParam Map<String, Object> map) {

    if (map == null) {

      return Result.error("0002", "参数传递错误！");
    }
    Map<String, Object> resultmap = questionNaire.commitSubject(map);
    return Result.ok(resultmap);
  }


  /**
   * 
   * @Author yuruyi
   * @Description 显示问卷正确结果
   * @Date 2019年3月10日
   * @Param
   * @return
   *
   */
  @RequestMapping("/findSubjectResult")
  public Result findSubjectResult(@RequestParam Map<String, Object> map) {

    if (map == null) {
      return Result.error("0002", "参数传递错误！");
    }


    questionNaire.findSubjectResult(map);
    return Result.ok("提交完成");
  }



  /**
   * 
   * @Author yuruyi
   * @Description 转发
   * @Date 2019年3月10日
   * @Param
   * @return
   *
   */
  @RequestMapping("/shareSubject")
  public Result ShareSubject(HttpServletRequest req, HttpServletResponse resp) {


    String strUrl = "https://www.queriestech.com/" // 换成安全域名
        + req.getContextPath() // 项目名称
        + req.getServletPath() // 请求页面或其他地址
        + "?" + (req.getQueryString()); // 参数

    String acesstoken = WxApiUtils.getAcessToken();

    String jsapi_ticket = WxApiUtils.getJsTiket(acesstoken);

    Map<String, String> retdata = WxApiUtils.getSignatureData(jsapi_ticket, strUrl);
    return Result.ok(retdata);
  }



  /***
   * 
   * @Author yuruyi
   * @Description 获取用户以做的的题目
   * @Date 2019年3月10日
   * @Param
   * @return
   *
   */
  @RequestMapping("/getAnserQuestioNaireList")
  public Result getSubjectList(@RequestParam Map<String, Object> map) {

    String openid = map.get("openid").toString();
    List<UserAnswer> list = questionNaire.getQuestionNaireList(openid);
    return Result.ok(list);
  }

}
