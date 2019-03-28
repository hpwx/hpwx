package com.hp.modules.sys.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hp.modules.sys.entity.PoJoSubjectInfo;
import com.hp.modules.sys.service.IQuestionStaticsService;
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
  IQuestionStaticsService staticsService;


  @RequestMapping(value = "getSingleQuestionStatics", method = RequestMethod.GET)
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
    List<PoJoSubjectInfo> list = staticsService.getSingleSubjectStatics(questionNaireId, typeid, pe);
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
  @RequestMapping(value = "getMutipQuestionStatics", method = RequestMethod.GET)
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
    List<PoJoSubjectInfo> list = staticsService.getMultipSubjectStatics(questionNaireId, typeid, pe);
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

  @RequestMapping(value = "getStarQuestionStatics", method = RequestMethod.GET)
  public Result getScoreQuestionStatistics(@RequestParam Map<String, Object> map) {

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
    List<PoJoSubjectInfo> list = staticsService.getScoreSubjectStatics(questionNaireId, typeid);
    PageUtils page = PageUtils.page(list);
    return Result.keyValueOk("page", page);
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
  @RequestMapping(value = "getCompletionQuestionStatics", method = RequestMethod.GET)
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
    List<PoJoSubjectInfo> list = staticsService.getCompletionSubjectStatics(questionNaireId, typeid);
    PageUtils page = PageUtils.page(list);
    return Result.keyValueOk("page", page);
  }

}
