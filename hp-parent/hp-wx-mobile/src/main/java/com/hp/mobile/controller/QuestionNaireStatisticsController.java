package com.hp.mobile.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hp.mobile.entity.PoJoSubjectInfo;
import com.hp.mobile.service.IQuestionStaticsService;
import com.ym.ms.entity.Result;
import com.ym.ms.exception.CodeMsgEnum;
import com.ym.ms.paging.PageUtils;
import com.ym.ms.paging.PagerEO;

@RestController
@RequestMapping("/questionnaireStatistics")

public class QuestionNaireStatisticsController {


  /**
   * 
   * @Author yuruyi
   * @Description 单选统计
   * @Date 2019年3月25日
   * @Param
   * @return
   *
   */


  @Autowired
  IQuestionStaticsService staticsServie;


  @RequestMapping(value = "getSingleQuestionStatics", method = RequestMethod.POST)
  public Result getsignleQuestionStatistics(@RequestParam Map<String, Object> map, PagerEO<?> pe) {

    String questionNaireId = null;
    String typeid = null;

    if (StringUtils.isEmpty(map)) {
      return Result.error(CodeMsgEnum.ERROR.getCode(), "map  为 null");
    }

    if (StringUtils.isEmpty(map.get("questionNaireId"))) {
      return Result.error(CodeMsgEnum.ERROR.getCode(), "questionNaireId  为 null");
    }
    if (StringUtils.isEmpty(map.get("typeid"))) {
      return Result.error(CodeMsgEnum.ERROR.getCode(), "typeid  为 null");
    }
    typeid = map.get("typeid").toString();
    questionNaireId = map.get("questionNaireId").toString();
    List<PoJoSubjectInfo> list = staticsServie.getSingleSubjectStatics(questionNaireId, typeid, pe);
    PageUtils page = PageUtils.page(list);
    return Result.keyValueOk("page", page);

  }


  /**
   * 
   * @Author yuruyi
   * @Description 多选统计
   * @Date 2019年3月25日
   * @Param
   * @return
   *
   */
  @RequestMapping(value = "getMutipQuestionStatics", method = RequestMethod.POST)
  public Result getMutipQuestionStatistics(@RequestParam Map<String, Object> map, PagerEO<?> pe) {

    String questionNaireId = null;
    String typeid = null;

    if (StringUtils.isEmpty(map)) {
      return Result.error(CodeMsgEnum.ERROR.getCode(), "map  为 null");
    }

    if (StringUtils.isEmpty(map.get("questionNaireId"))) {
      return Result.error(CodeMsgEnum.ERROR.getCode(), "questionNaireId  为 null");
    }
    if (StringUtils.isEmpty(map.get("typeid"))) {
      return Result.error(CodeMsgEnum.ERROR.getCode(), "typeid  为 null");
    }
    typeid = map.get("typeid").toString();
    questionNaireId = map.get("questionNaireId").toString();
    List<PoJoSubjectInfo> list = staticsServie.getMultipSubjectStatics(questionNaireId, typeid, pe);
    PageUtils page = PageUtils.page(list);
    return Result.keyValueOk("page", page);
  }

  /**
   * 
   * @Author yuruyi
   * @Description 打分题统计
   * @Date 2019年3月25日
   * @Param
   * @return
   *
   */

  @RequestMapping(value = "getScoreQuestionStatics", method = RequestMethod.POST)
  public void getScoreQuestionStatistics(@RequestParam Map<String, Object> map) {


  }

  /**
   * 
   * @Author yuruyi
   * @Description 填空题统计
   * @Date 2019年3月25日
   * @Param
   * @return
   *
   */
  @RequestMapping(value = "getCompletionQuestionStatics", method = RequestMethod.POST)
  public Result getCompletionQuestionStatics(@RequestParam Map<String, Object> map, PagerEO<?> pe) {

    String questionNaireId = null;
    String typeid = null;

    if (StringUtils.isEmpty(map)) {
      return Result.error(CodeMsgEnum.ERROR.getCode(), "map  为 null");
    }

    if (StringUtils.isEmpty(map.get("questionNaireId"))) {
      return Result.error(CodeMsgEnum.ERROR.getCode(), "questionNaireId  为 null");
    }
    if (StringUtils.isEmpty(map.get("typeid"))) {
      return Result.error(CodeMsgEnum.ERROR.getCode(), "typeid  为 null");
    }
    typeid = map.get("typeid").toString();
    questionNaireId = map.get("questionNaireId").toString();
    List<PoJoSubjectInfo> list = staticsServie.getCompletionSubjectStatics(questionNaireId, typeid);
    PageUtils page = PageUtils.page(list);
    return Result.keyValueOk("page", page);
  }

}
