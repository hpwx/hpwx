package com.hp.mobile.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.hp.mobile.entity.QquestionNaire;
import com.hp.mobile.entity.UserAnswer;
import com.hp.mobile.service.IQuestionNaire;
import com.hp.mobile.utils.WxApiUtils;
import com.ym.ms.entity.Result;

@RestController
@RequestMapping("/questionnaire")


public class QuestionNaireController {



  private final Logger LOG = LoggerFactory.getLogger(QuestionNaireController.class);
  @Autowired
  private IQuestionNaire questionNaire;



  /***
   * 
   * @Author yuruyi
   * @Description 获取问卷封面信息
   * @Date 2019年3月13日
   * @Param
   * @return
   *
   */

  @RequestMapping("/getQuestionNaireOver")
  public Result getQuestionNaireOver() {
    List<QquestionNaire> questionnaireInfo = questionNaire.getActiveQuestionNaireInfo();
    if (questionnaireInfo.size() == 0) {
      return Result.error("当前没有问卷活动");
    }
    return Result.ok(questionnaireInfo.get(0));
  }


  /**
   * 
   * @Author yuruyi
   * @Description 查询用户的问卷
   * @Date 2019年3月7日
   * @Param
   * @return
   *
   */
  @RequestMapping("/getSubjectListByQuestionNaireId")
  public Result getQuestionNaireByOpenId(@RequestParam("questionnaireid") String questionnaireid) {


    LOG.info(" 获取请求 getQuestionNaireByOpenId 接收参数： questionnaireid  " + questionnaireid);
    Map<String, Object> map = questionNaire.getUserQustionNarie(questionnaireid);
    return Result.ok(map);
  }

  /**
   * 
   * @Author yuruyi
   * @Description 用戶提交答卷
   * @Date 2019年3月11日
   * @Param
   * @return
   *
   */
  @RequestMapping(value = "/commitQustionNaire", method = RequestMethod.POST)
  public Result commitQustionNaire(@RequestBody Map<String, Object> map) {
    LOG.info(" 获取请求 commitQustionNaire 接收参数：" + JSON.toJSONString(map));
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
    LOG.info(" 获取请求 findSubjectResult 接收参数：" + JSON.toJSONString(map));
    if (map == null) {
      return Result.error("0002", "参数传递错误！");
    }
    Map<String, Object> retMap = questionNaire.findSubjectResult(map);
    return Result.ok(retMap);
  }

  /**
   * 
   * @Author yuruyi
   * @Description 转发分享题目
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


    LOG.info(" 获取请求 getAnserQuestioNaireList 接收参数：" + JSON.toJSONString(map));

    String openid = map.get("openid").toString();
    List<UserAnswer> list = questionNaire.getQuestionNaireList(openid);
    return Result.ok(list);
  }



  @RequestMapping("/checkQuestioNaire")
  public Result checkQuestioNaire(@RequestParam Map<String, Object> map) {
    if (!StringUtils.isEmpty(map.get("questionnareid"))) {
      String questionnareid = map.get("questionnareid").toString();
      Map<String, Object> retMap = questionNaire.checkQuestionNaireAnswer(questionnareid);

      if (Boolean.parseBoolean(retMap.get("isanswer").toString()) == false) {
        return Result.error(retMap.get("msg").toString());
      }
    }
    return Result.ok();
  }



}
