package com.hp.mobile.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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


  
   
  @Override
      /***
       * 获取题目列表
       * 
       */
  public Map<String, Object> getUserQustionNarie(String questionnaireid) {
    Map<String, Object> map = new HashMap<>();
    
    QquestionNaire qustionnareinfo=   qustionNaireMapper.selectByPrimaryKey(Long.parseLong(questionnaireid));
     if ( qustionnareinfo.getAnswerCount()!=null&&  qustionnareinfo.getAnswerpersoncount()!=null) {
        if (qustionnareinfo.getAnswerpersoncount()>=qustionnareinfo.getAnswerCount()) {
          throw new  SysException("该问卷作答次数已到 。无法在进行做题！");
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

    if (subjectlist == null) {

      throw new SysException(CodeMsgEnum.ERROR.getCode(), "题目不存在！");
    }

    List<Long> subjectlistIds = new ArrayList<>();

    for (PoJoSubjectInfo subject : subjectlist) {
      subjectlistIds.add(subject.getSubjectid());

    }
    List<TSurveyAnswers> items = pojoSubjectMapper.getAllChoiceitemBySubjectIds(subjectlistIds);

    setChoiceItems(subjectlist, items);
    subjectlist.sort(new Comparator<PoJoSubjectInfo>() {

      @Override
      public int compare(PoJoSubjectInfo o1, PoJoSubjectInfo o2) {
        
        return o1.getTypeid().compareTo(o2.getTypeid());
      }});
    
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
    String questionnairid = map.get("questionnairid").toString();
    String questionnairName = map.get("questionnairName").toString();
    // 提交的題目信息
   String  jsonsubjectlist=    JSONObject.toJSONString(map.get("subjectlist"));
    JSONArray jsonarray = JSONArray.parseArray( jsonsubjectlist);
    
    
//    JSONArray JSONArray = JSON.parseArray(map.get("subjectlist").toString());
    UserAnswer useranser = new UserAnswer();
    useranser.setOpenid(openid);
    useranser.setQuestionnaireId(Long.parseLong(questionnairid));
    useranser.setQustionNaireName(questionnairName);
    useranser.setCreateTime(new Date());
    userAnswerMapper.insertSelective(useranser);
    Long  commitId =  useranser.getObjectId();
    for (int i = 0; i < jsonarray.size(); i++) {

      JSONObject curentobj = jsonarray.getJSONObject(i);
      String subjectid = curentobj.get("subjectid").toString();
      String typeid = curentobj.get("typeid").toString();
      String subjectname = curentobj.get("subjectname").toString();

 
      
      
      // 回答问答题结果 填空題 、打分題結果
      String answertext =
          StringUtils.isEmpty( curentobj.get("answertext")) ? "" : curentobj.get("answertext").toString();

      // 1：单选题 2：多选题 3：填空题 4.打分 5. 选择题
      
     
      
          String  chocestring=  curentobj.get("choicelist").toString();

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
      if ("1".equals( typeid )  ||   "2".equals( typeid )  ||  "5" .equals( typeid) ) {
//        String[] arr = (String[]) choiclist.toArray();
//        String anserresultIds = StringUtils.join(arr);
        anserdetail.setAnswerResult(chocestring);
        anserdetail.setCorrectResult(subjectinfo.getSubjectAnswer());
      }
      // 3：填空题 4.打分
      if ( "3".equals( typeid )  ||  "4".equals( typeid ) ) {
        anserdetail.setAnswerResult(answertext);
        anserdetail.setCorrectResult(subjectinfo.getSubjectAnswer());
      }
      userAnserDetailMapper.insertSelective(anserdetail);
    }
    respmap.put("questionnaireId",questionnairid);
    respmap.put("questionnaireName", questionnairName);
    respmap.put("commitId", commitId);
    return respmap;
  }


  


  /**
   * 查詢 試卷题目結果
   */

  @Override
  public  Map<String, Object> findSubjectResult(Map<String, Object> map) {
    Map<String, Object> resultMap = new HashMap<>();
   // JSONObject subjectobject = new JSONObject();

    String openid = map.get("openid").toString();
    String questionnairId = map.get("questionnaireid").toString();
    String commitId = map.get("commitId").toString();   // 对应  T_useranser 表的   objectId
    QquestionNaire questionnairre =
        qustionNaireMapper.selectByPrimaryKey(Long.parseLong(questionnairId));

    List<UserAnswerDeatil>  anserdetaillist= userAnserDetailMapper.getAnserSubjectList(openid ,commitId);
     
    
    if (questionnairre==null) {
      
      throw new SysException(CodeMsgEnum.ERROR.getCode() ,"问卷信息不存在！");
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
   resultMap.put("count",  questionnairre.getAnswerCount()); // 表示 问卷允许做题次数
   resultMap.put("description", questionnairre.getQuestionnaireDesc());
    
   
   
   JSONArray    subjectArrayJson= new JSONArray();
    for (UserAnswerDeatil userAnswerDeatil : anserdetaillist) {
      JSONObject  subjectjson= new JSONObject();
      JSONArray  choicelistjson= new JSONArray();
      
        String  subjectype=   userAnswerDeatil.getSubjectType().toString();
        subjectjson.put("subjectid", userAnswerDeatil.getSubjectId());
        subjectjson.put("questionnaireid", userAnswerDeatil.getQuestionnaireId());
        subjectjson.put("name", userAnswerDeatil.getSubjectName());
        subjectjson.put("typeid", subjectype);
        subjectjson.put("answerResult",userAnswerDeatil.getAnswerResult() ); // 回答的結果    非填空題    存儲的是 Id 
        subjectjson.put("correctResult", userAnswerDeatil.getCorrectResult()); // 正確答案結果
         
      // 1：单选题 2：多选题 5. 选择题
      if (subjectype == "1" || subjectype == "2" || subjectype == "5") {
       // String[] choicelist =userAnswerDeatil.getAnswerResult().split(",")  ;
        List<TSurveyAnswers> choicelist=  surveyAnswersMapper.selectListBySubjectId(userAnswerDeatil.getSubjectId());
        choicelistjson.add(choicelist) ;
      }
      else {
        
      }
      
      subjectjson.put("choicelist", choicelistjson);
     
      
      subjectArrayJson.add(subjectjson);
    }
    
    resultMap.put("subjectlist", subjectArrayJson);
     
    
   
    return resultMap;
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
   *  根据 ID 获取问卷信息
   */
  @Override
   public    List<QquestionNaire>   getActiveQuestionNaireInfo( )  {
    // TODO Auto-generated method stub
    
     List<QquestionNaire>  list= qustionNaireMapper.selectQquestionNaireByEnable(Byte.parseByte("1"));
    
       return list; 
    
  }



  


 



}
