package com.hp.mobile.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.hp.mobile.entity.PoJoSubjectInfo;
import com.hp.mobile.entity.QquestionNaire;
import com.hp.mobile.entity.Subject;
import com.hp.mobile.entity.TSurveyAnswers;
import com.hp.mobile.entity.UserAnswer;
import com.hp.mobile.entity.UserAnswerDeatil;
import com.hp.mobile.mapper.PoJoSubjectInfoMapper;
import com.hp.mobile.mapper.QquestionNaireMapper;
import com.hp.mobile.mapper.QquestionNaireSubjectMapper;
import com.hp.mobile.mapper.SubjectMapper;
import com.hp.mobile.mapper.TSurveyAnswersMapper;
import com.hp.mobile.mapper.UserAnswerDeatilMapper;
import com.hp.mobile.mapper.UserAnswerMapper;
import com.hp.mobile.service.IQuestionNaire;
import com.hp.mobile.service.IuserService;
import com.ym.ms.exception.CodeMsgEnum;
import com.ym.ms.exception.SysException;

@Service
@Transactional
public class QustionNaireImpl implements IQuestionNaire {



  private final Logger LOG = LoggerFactory.getLogger(QustionNaireImpl.class);
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

  @Autowired

  UserAnswerMapper userAnswerMapper;



  private Object syslock = new Object();

  /***
   * 获取题目列表
   * 
   */
  @Override
  public Map<String, Object> getUserQustionNarie(String questionnaireid) {
    Map<String, Object> map = new HashMap<>();

    QquestionNaire qustionnareinfo =
        qustionNaireMapper.selectByPrimaryKey(Long.parseLong(questionnaireid));

    if (qustionnareinfo == null) {


      throw new SysException("该问卷已下线！");


    }
    if (qustionnareinfo.getAnswerCount() != null && qustionnareinfo.getAnswerCount() != 0
        && qustionnareinfo.getAnswerpersoncount() != null) {
      if (qustionnareinfo.getAnswerpersoncount() >= qustionnareinfo.getAnswerCount()) {
        throw new SysException("该问卷作答次数已到 ,无法在进行做题！");
      }
    }

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
      Long ispublic = qustionnareinfo.getIspublic();
      Date starttime = qustionnareinfo.getStartTime();
      Date endttime = qustionnareinfo.getEndTime();
      String icon = qustionnareinfo.getIcon();
      Byte isrepeatanswer = qustionnareinfo.getRepeatedAnswer();
      obj1.put("questionnaireid", qustionnareinfo.getObjectId());
      obj1.put("backimg", backimgurl);
      obj1.put("titile", titile);
      obj1.put("fengmian", fengmianurl);
      obj1.put("isanonymouns", isanonymouns);
      obj1.put("isforward", isforward);
      obj1.put("ispublic", ispublic);
      obj1.put("starttime", starttime);
      obj1.put("enddate", endttime);
      obj1.put("icon", icon);
      obj1.put("isrepeatanswer", isrepeatanswer);
    }



    List<PoJoSubjectInfo> subjectlist =
        pojoSubjectMapper.getSubjectList(Long.parseLong(questionnaireid));

    if (subjectlist == null || subjectlist.isEmpty()) {
      throw new SysException(CodeMsgEnum.ERROR.getCode(), "题目不存在！");
    }
    List<Long> subjectlistIds = new ArrayList<>();

    for (PoJoSubjectInfo subject : subjectlist) {

      ArrayList<String> arr = new ArrayList<String>();
      if (subject.getGradecount() != null) {
        for (Integer i = 1; i <= subject.getGradecount(); i++) {
          arr.add(i.toString());
        }
      }
      subject.setGradelist(arr);
      subjectlistIds.add(subject.getSubjectid());

    }
    List<TSurveyAnswers> items = pojoSubjectMapper.getAllChoiceitemBySubjectIds(subjectlistIds);

    setChoiceItems(subjectlist, items);
    subjectlist.sort(new Comparator<PoJoSubjectInfo>() {

      @Override
      public int compare(PoJoSubjectInfo o1, PoJoSubjectInfo o2) {

        return o1.getTypeid().compareTo(o2.getTypeid());
      }
    });

    obj2.put("subjectlist", subjectlist);
    obj1.putAll(obj2);
    obj1.putAll(obj2);
    map = JSONObject.toJavaObject(obj1, Map.class);
    // 是否修改答案



    return map;
  }

  /**
   * 
   * 
   * @Author yuruyi
   * @Description 交卷操作
   * @Date 2019年3月8日
   * @Param 前端前端传递过来 参数有 ： openId，questionnaireId， subjectId，choiceId， choiceId，
   * @return
   *
   */

  @Override
  public Map<String, Object> commitSubject(Map<String, Object> map) {


    Map<String, Object> respmap = new HashMap<String, Object>();
    String openid = map.get("openid").toString();

    String questionnairid = map.get("questionnaireid").toString();
    String questionnairName = map.get("questionnairName").toString();

    QquestionNaire questionnaireinfo =
        qustionNaireMapper.selectByPrimaryKey(Long.parseLong(questionnairid));



    List<UserAnswer> useranserlist = userAnswerMapper.getAnserQuestionNaireListByeOpenid(openid);

    if (questionnaireinfo.getRepeatedAnswer() == 0 && useranserlist.size() >= 1) {

      LOG.info("===================重复答题判断.........");
      throw new SysException(CodeMsgEnum.ERROR.getCode(), "该问卷不能重复作答！");


    }


    synchronized (syslock) {

      if (questionnaireinfo.getAnswerCount() != null) {

        Short personcount = questionnaireinfo.getAnswerpersoncount() == null ? 0
            : questionnaireinfo.getAnswerpersoncount();
        if (personcount >= questionnaireinfo.getAnswerCount()) {
          throw new SysException(CodeMsgEnum.ERROR.getCode(), "该问卷回答次数以达到上限！");
        }
        personcount++;
        questionnaireinfo.setAnswerpersoncount(personcount);
        LOG.info("=================== 答题上限盘点=========.........");
        qustionNaireMapper.updateByPrimaryKeySelective(questionnaireinfo);
      }

    }



    // 提交的題目信息
    String jsonsubjectlist = JSONObject.toJSONString(map.get("subjectlist"));
    JSONArray jsonarray = JSONArray.parseArray(jsonsubjectlist);

    // JSONArray JSONArray = JSON.parseArray(map.get("subjectlist").toString());
    UserAnswer useranser = new UserAnswer();
    useranser.setOpenid(openid);
    useranser.setQuestionnaireId(Long.parseLong(questionnairid));
    useranser.setQustionNaireName(questionnairName);
    useranser.setCreateTime(new Date());
    userAnswerMapper.insertSelective(useranser);
    Long commitId = useranser.getObjectId();
    for (int i = 0; i < jsonarray.size(); i++) {

      JSONObject curentobj = jsonarray.getJSONObject(i);
      String subjectid = curentobj.get("subjectid").toString();
      String typeid = curentobj.get("typeid").toString();
      String subjectname = curentobj.get("subjectname").toString();

      // 回答问答题结果 填空題 、打分題結果
      String answertext = StringUtils.isEmpty(curentobj.get("answertext")) ? ""
          : curentobj.get("answertext").toString();

      // 1：单选题 2：多选题 3：填空题 4.打分 5. 选择题


      List<Object> choiclist = (List<Object>) curentobj.get("choicelist");
      // JSONArray choiclist = JSON.parseArray(curentobj.get("choicelist").toString());


      // String chocestring= curentobj.get("choicelist").toString();

      Subject subjectinfo = subjectMapper.selectByPrimaryKey(Long.valueOf(subjectid));


      UserAnswerDeatil anserdetail = new UserAnswerDeatil();
      anserdetail.setOpenId(openid);
      anserdetail.setQuestionnaireId(Long.parseLong(questionnairid));
      anserdetail.setSubjectId(Long.parseLong(subjectid));
      anserdetail.setSubjectType(Integer.parseInt(typeid));

      anserdetail.setSerialnum(null);
      anserdetail.settUserAnswerId(Long.parseLong(commitId.toString()));
      anserdetail.setQuestionnaireName(questionnairName);
      anserdetail.setSubjectName(subjectname);
      anserdetail.setAnswerTime(new Date());

      // 1：单选题 2：多选题 5. 选择题
      if ("1".equals(typeid) || "2".equals(typeid) || "5".equals(typeid)) {

        String ansserresultids = Joiner.on(",").join(choiclist);
        anserdetail.setAnswerResult(ansserresultids); // 回答结果
        anserdetail.setCorrectResult(subjectinfo.getSubjectAnswer());

        // 修改投票次数
        surveyAnswersMapper.updateByobjectIds(Arrays.asList(ansserresultids.split(",")));

        //
        // String[] arr= subjectinfo.getSubjectAnswer().split(",");
        //
        // String rightext="";
        // String righttext1="";
        // for (String v : arr) {
        // TSurveyAnswers choice= surveyAnswersMapper.selectByPrimaryKey( Long.parseLong( v));
        // rightext += choice.getChoiceText();
        // }
        // if ( rightext.lastIndexOf(",")!=-1) {
        // righttext1= rightext.substring(0, rightext.length() -1);
        // }
      }
      // 3：填空题 4.打分
      if ("3".equals(typeid) || "4".equals(typeid)) {
        anserdetail.setAnswerResult(answertext); // 回答结果
        anserdetail.setCorrectResult(subjectinfo.getSubjectAnswer());
      }
      userAnserDetailMapper.insertSelective(anserdetail);
    }



    respmap.put("endIsShow", questionnaireinfo.getEndisshow());
    respmap.put("endimage", questionnaireinfo.getEndimage());
    respmap.put("enddesc", questionnaireinfo.getEndimagedesc());
    respmap.put("isshare", questionnaireinfo.getIsPubic());
    respmap.put("ispbulic", questionnaireinfo.getIsPubic());
    respmap.put("questionnaireId", questionnairid);
    respmap.put("questionnaireName", questionnairName);
    respmap.put("commitId", commitId);
    return respmap;
  }



  /**
   * 查詢 試卷题目結果
   */

  @Override
  public Map<String, Object> findSubjectResult(Map<String, Object> map) {
    Map<String, Object> resultMap = new HashMap<>();
    // JSONObject subjectobject = new JSONObject();

    String openid = map.get("openid").toString();
    String questionnairId = map.get("questionnaireid").toString();
    String commitId = map.get("commitId").toString(); // 对应 T_useranser 表的 objectId
    QquestionNaire questionnairre =
        qustionNaireMapper.selectByPrimaryKey(Long.parseLong(questionnairId));

    List<UserAnswerDeatil> anserdetaillist =
        userAnserDetailMapper.getAnserSubjectList(openid, commitId);


    if (questionnairre == null) {

      throw new SysException(CodeMsgEnum.ERROR.getCode(), "问卷信息不存在！");
    }
    resultMap.put("backimg", questionnairre.getBackColor());
    resultMap.put("titile", questionnairre.getTitle());
    resultMap.put("fengmian", questionnairre.getCover());
    resultMap.put("isanonymouns", questionnairre.getAnonymous());
    resultMap.put("isforward", questionnairre.getForward());
    resultMap.put("ispublic", questionnairre.getIspublic());
    resultMap.put("starttime", questionnairre.getStartTime());
    resultMap.put("enddate", questionnairre.getEnable());
    resultMap.put("icon", questionnairre.getIcon());
    resultMap.put("isrepeatanswer", questionnairre.getRepeatedAnswer());
    resultMap.put("count", questionnairre.getAnswerCount()); // 表示 问卷允许做题次数
    resultMap.put("description", questionnairre.getQuestionnaireDesc()); // 文件描述

    resultMap.put("endIsShow", questionnairre.getEndisshow());
    resultMap.put("endimage", questionnairre.getEndimage());
    resultMap.put("enddesc", questionnairre.getEndimagedesc());
    resultMap.put("isshare", questionnairre.getIsPubic());
    Integer errortsubjectcount = 0;
    Integer rightsubjectcount = 0;

    JSONArray subjectArrayJson = new JSONArray();
    for (UserAnswerDeatil userAnswerDeatil : anserdetaillist) {
      JSONObject subjectjson = new JSONObject();
      // JSONArray choicelistjson= new JSONArray();
      List<TSurveyAnswers> choicelist = new ArrayList<TSurveyAnswers>();
      String subjectype = userAnswerDeatil.getSubjectType().toString();
      subjectjson.put("subjectid", userAnswerDeatil.getSubjectId());
      subjectjson.put("questionnaireid", userAnswerDeatil.getQuestionnaireId());
      subjectjson.put("name", userAnswerDeatil.getSubjectName());
      subjectjson.put("typeid", subjectype);


      subjectjson.put("answerResult", userAnswerDeatil.getAnswerResult()); // 回答的結果 非填空題 存儲的是 Id
      subjectjson.put("correctResult", userAnswerDeatil.getCorrectResult()); // 正確答案結果

      ArrayList<String> gradelist = new ArrayList<String>();

      if ("4".equals(subjectype)) {
        if (userAnswerDeatil.getAnswerResult() != null) {

          LOG.info("====打分 个数============：" + userAnswerDeatil.getAnswerResult());
          for (Integer i = 1; i <= Integer.valueOf(userAnswerDeatil.getAnswerResult()); i++) {
            gradelist.add(i.toString());
          }
        }

      }
      subjectjson.put("gradelist", gradelist);


      subjectjson.put("rightext", "");
      subjectjson.put("isright", "");
      // 1：单选题 2：多选题 5. 选择题
      if ("1".equals(subjectype) || "2".equals(subjectype) || "5".equals(subjectype)) {

        choicelist = surveyAnswersMapper.selectListBySubjectId(userAnswerDeatil.getSubjectId());

        LOG.info("  正确答案  ：选项IDs ：" + userAnswerDeatil.getCorrectResult());

        List<String> arr = Arrays.asList(userAnswerDeatil.getCorrectResult().split(","));

        List<TSurveyAnswers> list = surveyAnswersMapper.selectListCorretAnaser(arr);

        LOG.info("   获取选项文本 个数  ：" + list.size());
        List<String> righttextlist = new ArrayList<String>();

        for (TSurveyAnswers tsa : list) {
          LOG.info("正确答案选项文本 ：============" + tsa.getChoiceText());
          righttextlist.add(tsa.getChoiceText());
        }
        if (righttextlist.size() == 0) {
          subjectjson.put("rightext", ""); // 正确答案
        } else {
          LOG.info("题目    " + userAnswerDeatil.getSubjectName() + "  正确答案    ：============"
              + String.join(",", righttextlist));
          subjectjson.put("rightext", String.join(",", righttextlist));
        }

        if (userAnswerDeatil.getAnswerResult().equals(userAnswerDeatil.getCorrectResult())) {
          subjectjson.put("isright", "正确"); // 正确答案
          rightsubjectcount++;

        } else {
          subjectjson.put("isright", "错误");
          errortsubjectcount++;
        }
      } else {


      }
      subjectjson.put("choicelist", choicelist);


      subjectArrayJson.add(subjectjson);
    }

    resultMap.put("subjectlist", subjectArrayJson);

    resultMap.put("rightsubjectcount", rightsubjectcount);
    resultMap.put("errortsubjectcount", errortsubjectcount);

    return resultMap;
  }


  private String getRightAanser(TSurveyAnswers surveranswer, String rightAnser) {
    String choicetext = "";
    // 单选
    if (rightAnser.equals(surveranswer.getObjectId().toString())) {

      choicetext = surveranswer.getChoiceText();
    } else {
      String[] arr = rightAnser.split(",");
      for (String id : arr) {
        if (surveranswer.getObjectId().toString().equals(id)) {
          choicetext += surveranswer.getChoiceText() + ",";
        } else {

        }

      }


    }
    if (choicetext.contains(",")) {
      return choicetext.substring(0, choicetext.length() - 1);

    }

    return choicetext;
  }

  /***
   * 
   * 获取 用户 做过的问卷问卷列表
   */
  @Override
  public List<UserAnswer> getQuestionNaireList(String openid) {
    List<UserAnswer> list = userAnswerMapper.getAnserQuestionNaireListByeOpenid(openid);
    return list;

  }

  private void setChoiceItems(List<PoJoSubjectInfo> subjectlist, List<TSurveyAnswers> answerlist) {

    for (PoJoSubjectInfo subject : subjectlist) {

      List<Map<String, Object>> lsit = new ArrayList<>();
      for (TSurveyAnswers sub : answerlist) {
        if (sub.getQuestionId().equals(subject.getSubjectid())) {
          Map<String, Object> item = new HashMap<>();
          item.put("choiceId", sub.getObjectId());
          item.put("choicetext", sub.getChoiceText());
          item.put("subjectId", sub.getQuestionId());
          lsit.add(item);
        }
        subject.setList(lsit);
      }
    }


  }



  // 获取题目选项
  private List<TSurveyAnswers> getsubjectitems(List<Long> subjectIdslist) {

    List<TSurveyAnswers> items = pojoSubjectMapper.getAllChoiceitemBySubjectIds(subjectIdslist);
    return items;
  }


  private List<PoJoSubjectInfo> getAnserSubjectList(UserAnswerDeatil useranserdetail,
      List<PoJoSubjectInfo> subjectlist, List<TSurveyAnswers> items) {


    for (PoJoSubjectInfo subject : subjectlist) {

      List<Map<String, Object>> lsit = new ArrayList<>();

      Map<String, Object> rightresult = new HashMap<>();

      if (useranserdetail.getCorrectResult().equals(useranserdetail.getAnswerResult())) {
        rightresult.put("isright", true);
      } else {
        rightresult.put("isright", false);
      }
      rightresult.put("correctresult", useranserdetail.getCorrectResult());

      for (TSurveyAnswers sub : items) {
        if (sub.getQuestionId().equals(subject.getSubjectid())) {
          Map<String, Object> item = new HashMap<>();
          item.put("choiceId", sub.getObjectId());
          item.put("choicetext", sub.getChoiceText());
          item.put("subjectId", sub.getQuestionId());

          lsit.add(item);
          subject.setList(lsit);
        }
      }
      subject.setResultinfo(rightresult);

    }

    return subjectlist;
  }


  /***
   * 根据 ID 获取问卷信息
   */
  @Override
  public List<QquestionNaire> getActiveQuestionNaireInfo() {
    // TODO Auto-generated method stub

    List<QquestionNaire> list =
        qustionNaireMapper.selectQquestionNaireByEnable(Byte.parseByte("1"));
    return list;
  }

  @Override
  public Map<String, Object> checkQuestionNaireAnswer(String questionnaireid, String openid) {

    Map<String, Object> retmap = new HashMap<>();

    retmap.put("isanswer", true);
    retmap.put("msg", "");
    QquestionNaire qustionnareinfo =
        qustionNaireMapper.selectByPrimaryKey(Long.parseLong(questionnaireid));

    LOG.info("校验接口接口： 问卷ID " + questionnaireid + "  -===== 问卷允许作答次数："
        + qustionnareinfo.getAnswerCount());
    if (qustionnareinfo.getAnswerCount() != null) {
      Short personcount = qustionnareinfo.getAnswerpersoncount() == null ? 0
          : qustionnareinfo.getAnswerpersoncount();

      LOG.info("问卷已作答个数 ：======   " + personcount);

      if (personcount >= qustionnareinfo.getAnswerCount()) {
        retmap.put("isanswer", false);
        retmap.put("msg", "该问卷做题次数已达到上限！");

        LOG.info("已达到 上限.................");
      }
    }

    // if (qustionnareinfo.getAnswerCount() != null && qustionnareinfo.getAnswerCount() != 0
    // && qustionnareinfo.getAnswerpersoncount() != null) {
    // if (qustionnareinfo.getAnswerpersoncount() >= qustionnareinfo.getAnswerCount()) {
    // retmap.put("isanswer", false);
    // retmap.put("msg", "该问卷做题次数已达到上限！");
    // }
    // }
    // 不是重复答
    if (qustionnareinfo.getRepeatedAnswer() == null || qustionnareinfo.getRepeatedAnswer() == 0) {
      List<UserAnswer> useranserlist = userAnswerMapper.getAnserQuestionNaireListByeOpenid(openid);
      if (useranserlist.size() > 0) {
        retmap.put("isanswer", false);
        retmap.put("msg", "该问卷不能重复作答！");
      }
    }



    return retmap;
  }



}
