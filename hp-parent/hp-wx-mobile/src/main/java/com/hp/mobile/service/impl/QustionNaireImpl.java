package com.hp.mobile.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hp.mobile.entity.PoJoSubjectInfo;
import com.hp.mobile.entity.QquestionNaire;
import com.hp.mobile.entity.TSurveyAnswers;
import com.hp.mobile.entity.UserInfo;
import com.hp.mobile.mapper.PoJoSubjectInfoMapper;
import com.hp.mobile.mapper.QquestionNaireMapper;
import com.hp.mobile.mapper.QquestionNaireSubjectMapper;
import com.hp.mobile.mapper.SubjectMapper;
import com.hp.mobile.mapper.TSurveyAnswersMapper;
import com.hp.mobile.mapper.UserAnswerDeatilMapper;
import com.hp.mobile.service.IQuestionNaire;
import com.hp.mobile.service.IuserService;
import com.ym.ms.exception.CodeMsgEnum;
import com.ym.ms.exception.SysException;

@Service
public class QustionNaireImpl implements IQuestionNaire {
  @Autowired
  private IuserService userSerice;

  @Autowired
  QquestionNaireMapper qustionNaireMapper;

  @Autowired
  private SubjectMapper subjectMapper;


  @Autowired
  private QquestionNaireSubjectMapper questionNaireSubject;


  @Autowired
  private UserAnswerDeatilMapper userAnserDetailMapper;


  @Autowired
  private PoJoSubjectInfoMapper pojoSubjectMapper;


  @Autowired
  private TSurveyAnswersMapper surveyAnswersMapper;

  // 获取题目
  public Map<String, Object> getUserQustionNarie(String openId) {

    Map<String, Object> map = new HashMap<>();

    UserInfo userinfo = userSerice.getUserByOpenId(openId);
    if (userinfo == null || userinfo.getQuestionnaireIds() == null) {
      throw new SysException("00001", " 没有问卷信息");
    }
    // 查询问卷ID
    String questionnaireid = userinfo.getQuestionnaireIds();


    QquestionNaire qustionnareinfo =
        qustionNaireMapper.selectByPrimaryKey(Long.parseLong(questionnaireid));


    JSONObject obj1 = new JSONObject();

    JSONObject obj2 = new JSONObject();

    if (qustionnareinfo != null) {
      // 背景图片
      String backimgurl = qustionnareinfo.getBackColor();
      // 标题
      String titile = qustionnareinfo.getTitle();
      // 背景图片url
      String fengmianurl = qustionnareinfo.getCover();
      // 是否匿名
      Byte isanonymouns = qustionnareinfo.getAnonymous();

      // 是否启用
      Byte ienable = qustionnareinfo.getEnable();
      // 是否转发
      Byte isforward = qustionnareinfo.getForward();
     Long   ispublic = qustionnareinfo.getIspublic();
     Date   starttime = qustionnareinfo.getStartTime();
     Date   endttime = qustionnareinfo.getEndTime() ;
    String icon=  qustionnareinfo.getIcon();
    Byte isrepeatanswer= qustionnareinfo.getRepeatedAnswer();
      obj1.put("backimg", backimgurl);
      obj1.put("titile", titile);
      obj1.put("fengmian", fengmianurl);
      obj1.put("isanonymouns", isanonymouns);
      obj1.put("isforward", isforward);
      obj1.put("ispublic",ispublic );
      obj1.put( "starttime",starttime);
      obj1.put("enddate", endttime);
      
      obj1.put("icon", icon);
      obj1.put("isrepeatanswer", isrepeatanswer);
    
    }

    List<PoJoSubjectInfo> subjectlist =
        pojoSubjectMapper.getSubjectList(Long.parseLong(questionnaireid));

    if (subjectlist == null) {

      throw new SysException(CodeMsgEnum.ERROR.getCode(), "题目不存在！");
    }

    List<Long> subjectlistIds = new ArrayList<>();

    for (PoJoSubjectInfo subject : subjectlist) {
      subjectlistIds.add(subject.getSubjectid());

    }
      List<TSurveyAnswers> items = pojoSubjectMapper.getAllChoiceitemBySubjectIds(subjectlistIds);

      setChoiceItems(subjectlist, items);

    obj2.put("subjectlist", subjectlist);
    obj1.putAll(obj2);
    obj1.putAll(obj2);


    map = JSONObject.toJavaObject(obj1, Map.class);
    // 是否修改答案



    return map;
  }

  private void setChoiceItems(List<PoJoSubjectInfo> subjectlist, List<TSurveyAnswers> answerlist) {

    for (PoJoSubjectInfo subject : subjectlist) {

      List<Map<String, Object>> lsit = new ArrayList<>();
      for (TSurveyAnswers sub : answerlist) {
        if (sub.getQuestionId().equals( subject.getSubjectid())) {
          Map<String, Object> item = new HashMap<>();
          item.put("choicetext", sub.getChoiceText());
          item.put("subjectId", sub.getQuestionId());
          lsit.add(item);
        }
        subject.setList(lsit);
      }
    }


  }



}
