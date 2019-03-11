package com.hp.mobile.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.hp.mobile.entity.PoJoSubjectInfo;
import com.hp.mobile.entity.QquestionNaire;
import com.hp.mobile.entity.Subject;
import com.hp.mobile.entity.TSurveyAnswers;
import com.hp.mobile.entity.UserAnswer;
import com.hp.mobile.entity.UserAnswerDeatil;
import com.hp.mobile.entity.UserInfo;
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
import com.ym.utils.util.TokenGenerator;

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
  
  UserAnswerMapper  userAnswerMapper;
  
  

  // 获取题目
  public Map<String, Object> getUserQustionNarie(String openId) {

    Map<String, Object> map = new HashMap<>();

    UserInfo userinfo = userSerice.getUserByOpenId(openId);
    if (userinfo == null || userinfo.getQuestionnaireIds() == null) {
      throw new SysException(CodeMsgEnum.ERROR.getCode(), " 没有问卷信息");
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

 
  
   /**
    * 
    * 
    * @Author yuruyi
    * @Description     交卷操作
    * @Date   2019年3月8日
    * @Param  
    * 前端前端传递过来 参数有 ：
    *   openId，questionnaireId，
    *   subjectId，choiceId，
    *   choiceId，
    * @return  
    *
    */
  public    Map<String,Object>   commitSubject (Map<String ,Object> map) {
    
    
    String openid  =map.get("openId").toString();
    //  问卷ID
    String questionnaireid =   map.get("questionnaireId").toString();
     // 题目ID
    String  subjectIds=null;
        if (map.get("subjectIds")!=null) {
          
             subjectIds=map.get("subjectIds").toString();  // 多个用，分割
        }
    
        //  选项ID 
        String  chociceid =map.get("choiceId").toString();  // 多个用,分割
          // 选项文本
        String  chocicetext =map.get("chocetxt").toString();
     
    
    // 查询问卷
         QquestionNaire   questionnaire=  qustionNaireMapper.selectByPrimaryKey(Long.parseLong(questionnaireid));
     
        // 題目列表
        List<String> list=   Arrays.asList  (subjectIds.split(",")) ;
     
        List<Subject> subjectlist= subjectMapper.selectListBySubjectId(list);
      
     
        
    //  插入 用戶问卷表
    UserAnswer uerAnswer= new  UserAnswer();
    uerAnswer.setOpenid(openid);
    uerAnswer.setCreateTime(new Date());
    uerAnswer.setQuestionnaireId( Long.valueOf(questionnaireid));
    uerAnswer.setSubjectids(subjectIds);
  
     String   serialnum= TokenGenerator.generateValue() ;
      uerAnswer.setAnswerSerialNum(serialnum);
     int result=  userAnswerMapper.insert(uerAnswer);
    
    
      if (result>0) {
        for (Subject item : subjectlist) {
          
          UserAnswerDeatil  anserdetail= new UserAnswerDeatil();
           
          anserdetail.setOpenId(openid);
          anserdetail.setSubjectId(item.getObjectId());
          anserdetail.setSubjectName(item.getName()); 
          anserdetail.setAnswerResult(chociceid); // 回答結果
          anserdetail.setCorrectResult(item.getSubjectAnswer());// 正确答案
          anserdetail.setQuestionnaireId(questionnaire.getObjectId()); // 问卷ID
          anserdetail.setQuestionnaireName(questionnaire.getTitle());// 问卷名称
          anserdetail.setSubjectType(item.getTypeId()); // 题型
          anserdetail.settUserAnswerId(null); // 回答问卷表的ID
          anserdetail.setSerialnum(serialnum); // 流水号 关联 useranswer 表的     
          anserdetail.setAnswerScore(null);
          anserdetail.setAnswerTime(new Date());
          userAnserDetailMapper.insert(anserdetail);
          
          
        }
      }
      
      
      map.put("serialnum", serialnum);
      return map;
  }
   
  
  private void setChoiceItems(List<PoJoSubjectInfo> subjectlist, List<TSurveyAnswers> answerlist) {

    for (PoJoSubjectInfo subject : subjectlist) {

      List<Map<String, Object>> lsit = new ArrayList<>();
      for (TSurveyAnswers sub : answerlist) {
        if (sub.getQuestionId().equals( subject.getSubjectid())) {
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



         /**
          * 查詢 試卷結果
          */
  
  @Override
  public void findSubjectResult(Map<String, Object> map) {
    
    
    
        Map<String,Object> resultMap= new  HashMap<>();
        
        JSONObject subjectobject= new JSONObject();
         
        
        
        String openid=  map.get("openid").toString();
        String questionnairId=  map.get("questionnaireid").toString();
       
        String serialnum= map.get("serialnum").toString();
        
        QquestionNaire  questionnairre=   qustionNaireMapper.selectByPrimaryKey(Long.parseLong(questionnairId)) ;
     
        if (questionnairre==null   ) {
          return ;
        }
          //描述信息
          String description =  questionnairre.getQuestionnaireDesc();
          map.put("description", description);
        //如果是公开的 则显示正确答案 
          
             
            List<UserAnswerDeatil> userAnserDetailList= userAnserDetailMapper.getAnserSubjectList(openid, serialnum, questionnairre.getObjectId()) ;
           
            List<PoJoSubjectInfo> subjectlist =    pojoSubjectMapper.getSubjectList( Long.parseLong(  questionnairId ));
            if (subjectlist == null) {

              throw new SysException(CodeMsgEnum.ERROR.getCode(), "题目不存在！");
            }

            List<Long> subjectlistIds = new ArrayList<>();

            for (PoJoSubjectInfo subject : subjectlist) {
              subjectlistIds.add(subject.getSubjectid());
            }
              
           List<TSurveyAnswers> items =  getsubjectitems(subjectlistIds);
            
           for (UserAnswerDeatil userAnswerDeatil : userAnserDetailList) {
             
             
             getAnserSubjectList( userAnswerDeatil ,subjectlist  , items);
          }
            resultMap.put("isbulic", questionnairre.getIsPubic());
            resultMap.put("subjectlist", subjectlist);
            resultMap.put("isshare", questionnairre.getForward()); 
    
  }

  
  // 获取题目选项
    private  List<TSurveyAnswers>  getsubjectitems(  List<Long>   subjectIdslist) {
      
      List<TSurveyAnswers> items = pojoSubjectMapper.getAllChoiceitemBySubjectIds(subjectIdslist);
      return items;
    }  
    
   
    private  List<PoJoSubjectInfo> getAnserSubjectList(  UserAnswerDeatil  useranserdetail  , List<PoJoSubjectInfo> subjectlist ,List<TSurveyAnswers>  items) {
      
      
        for (PoJoSubjectInfo subject : subjectlist) {

          List<Map<String, Object>> lsit = new ArrayList<>();
           
          Map<String,Object> rightresult= new HashMap<>();
          
          if(useranserdetail.getCorrectResult().equals(useranserdetail.getAnswerResult()))
          { 
            rightresult.put("isright", true); 
          }
          else {
           rightresult.put("isright", false);
         }
          rightresult.put("correctresult", useranserdetail.getCorrectResult() );
          
          for (TSurveyAnswers sub : items) {
            if (sub.getQuestionId().equals( subject.getSubjectid())) {
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
        
        return  subjectlist;
    }

 
   /**
    *  获取 用户 做过的问卷问卷列表
    */
   
    public  List<UserAnswer>  getQuestionNaireList(String openid) {
      // 
     return   userAnswerMapper.getAnserQuestionNaireListByeOpenid(openid);
                
       
    }

}
