package com.hp.mobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.hp.mobile.entity.PoJoSubjectInfo;
import com.hp.mobile.entity.PollStarInfo;
import com.hp.mobile.entity.TSurveyAnswers;
import com.hp.mobile.mapper.PoJoSubjectInfoMapper;
import com.hp.mobile.mapper.TSurveyAnswersMapper;
import com.hp.mobile.mapper.UserAnswerDeatilMapper;
import com.hp.mobile.service.IQuestionStaticsService;
import com.ym.ms.paging.PagerEO;

@Service
public class QuestionStaticsServiceImpl implements IQuestionStaticsService {


  @Autowired
  PoJoSubjectInfoMapper subjectinfoMapper;



  @Autowired
  TSurveyAnswersMapper ChoceAnsersMapper;


  @Autowired
  UserAnswerDeatilMapper userAnserDetailMapper;

  /**
   * 单选统计
   */
  @Override
  public List<PoJoSubjectInfo> getSingleSubjectStatics(String questionNaireId, String typeid,
      PagerEO<?> pe) {

    PageHelper.startPage(pe.getPage(), pe.getLimit());

    List<PoJoSubjectInfo> subjectlist =
        subjectinfoMapper.getSubjectListByQustionNaireIdAndSubjectType(
            Long.parseLong(questionNaireId), Integer.valueOf(typeid));
    for (PoJoSubjectInfo subjectInfo : subjectlist) {
      Long subjectid = subjectInfo.getSubjectid();
      List<TSurveyAnswers> surveryAnserslist = ChoceAnsersMapper.selectListBySubjectId(subjectid);

      BuiderSubjectlist(subjectInfo, surveryAnserslist);
    }

    return subjectlist;
  }


  /**
   * 多选统计
   */
  @Override
  public List<PoJoSubjectInfo> getMultipSubjectStatics(String questionNaireId, String typeid,
      PagerEO<?> pe) {



    PageHelper.startPage(pe.getPage(), pe.getLimit());

    List<PoJoSubjectInfo> subjectlist =
        subjectinfoMapper.getSubjectListByQustionNaireIdAndSubjectType(
            Long.parseLong(questionNaireId), Integer.valueOf(typeid));
    for (PoJoSubjectInfo subjectInfo : subjectlist) {
      Long subjectid = subjectInfo.getSubjectid();
      List<TSurveyAnswers> surveryAnserslist = ChoceAnsersMapper.selectListBySubjectId(subjectid);

      BuiderSubjectlist(subjectInfo, surveryAnserslist);
    }



    return subjectlist;

  }


  /***
   * 打分题
   */
  @Override
  public List<PoJoSubjectInfo> getScoreSubjectStatics(String questionNaireId, String typeid) {

    List<PoJoSubjectInfo> subjectlist = subjectinfoMapper
        .getSubjectListForScoreStatics(Long.parseLong(questionNaireId), Integer.valueOf(typeid));

    for (PoJoSubjectInfo subjectinfo : subjectlist) {
      Integer totalPoll = userAnserDetailMapper.getToalPoll(questionNaireId,
          subjectinfo.getSubjectid().toString(), typeid);
      Integer starcount = subjectinfo.getGradecount();
      Long subjectId = subjectinfo.getSubjectid();
      // 星星个数

      List<PollStarInfo> pollstarInfo = new ArrayList<>();

      for (int i = starcount; i >= 1; i--) {
        PollStarInfo p = new PollStarInfo();
        p.setStarnum(i);// 星星数
        Integer starnumpoll = userAnserDetailMapper.getPollByStarNum(String.valueOf(i), subjectId);
        p.setPoll(starnumpoll); // 投票 数量
        p.setTotalpoll(totalPoll); // 投票总数
        pollstarInfo.add(p);
      }
      subjectinfo.setPolllist(pollstarInfo);
    }
    return subjectlist;
  }



  /***
   * 填空题
   */
  @Override
  public List<PoJoSubjectInfo> getCompletionSubjectStatics(String questionNaireId, String typeid) {
    // TODO Auto-generated method stub
    List<PoJoSubjectInfo> subjectlist =
        subjectinfoMapper.getSubjectListByQustionNaireIdAndSubjectType(
            Long.parseLong(questionNaireId), Integer.valueOf(typeid));



    return subjectlist;

  }



  /***
   * 
   * @Author yuruyi
   * @Description 将选项拼接到 subject下的 choicelist
   * @Date 2019年3月25日
   * @Param
   * @return
   *
   */

  private void BuiderSubjectlist(PoJoSubjectInfo pojosubjectinfo,
      List<TSurveyAnswers> surveryanserlist) {
    List<Map<String, Object>> lsit = new ArrayList<>();

    Integer totalpoll = 0;
    for (TSurveyAnswers sub : surveryanserlist) {
      Map<String, Object> item = new HashMap<>();
      item.put("subjectId", sub.getQuestionId());
      item.put("choiceId", sub.getObjectId());
      item.put("choicetext", sub.getChoiceText());
      item.put("poll", sub.getPoll());
      lsit.add(item);
      Integer curentpoll = sub.getPoll() == null ? 0 : sub.getPoll();
      totalpoll += curentpoll;
      pojosubjectinfo.setChocelist(lsit);
    }
    pojosubjectinfo.setTotalpoll(totalpoll);

  }



}
