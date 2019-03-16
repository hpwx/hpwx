package com.hp.mobile.entity;

import java.util.List;
import java.util.Map;

public class PoJoSubjectInfo {

  public Long getSubjectid() {
    return subjectid;
  }

  public void setSubjectid(Long subjectid) {
    this.subjectid = subjectid;
  }

  public Long getQuestionnaireid() {
    return questionnaireid;
  }

  public void setQuestionnaireid(Long questionnaireid) {
    this.questionnaireid = questionnaireid;
  }
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
  public String getBackcolor() {
    return backcolor;
  }

  public void setBackcolor(String backcolor) {
    this.backcolor = backcolor;
  }

  public Long getIspublic() {
    return ispublic;
  }

  public void setIspublic(Long ispublic) {
    this.ispublic = ispublic;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Byte getTypeid() {
    return typeid;
  }

  public void setTypeid(Byte typeid) {
    this.typeid = typeid;
  }

  public String getSubjectanswer() {
    return subjectanswer;
  }

  public void setSubjectanswer(String subjectanswer) {
    this.subjectanswer = subjectanswer;
  }

  public Byte getMustanswer() {
    return mustanswer;
  }

  public void setMustanswer(Byte mustanswer) {
    this.mustanswer = mustanswer;
  }

  public Byte getAnonymous() {
    return anonymous;
  }

  public void setAnonymous(Byte anonymous) {
    this.anonymous = anonymous;
  }

  public Byte getForward() {
    return forward;
  }

  public void setForward(Byte forward) {
    this.forward = forward;
  }

  public Byte getRepeatedanswer() {
    return repeatedanswer;
  }

  public void setRepeatedanswer(Byte repeatedanswer) {
    this.repeatedanswer = repeatedanswer;
  }

  public String getAnswercount() {
    return answercount;
  }

  public void setAnswercount(String answercount) {
    this.answercount = answercount;
  }

  public Byte getQuestionnairedesc() {
    return questionnairedesc;
  }

  public void setQuestionnairedesc(Byte questionnairedesc) {
    this.questionnairedesc = questionnairedesc;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  
  public String getChoicetext() {
    return choicetext;
  }

  public void setChoicetext(String choicetext) {
    this.choicetext = choicetext;
  }
  private  Long subjectid;
  private  Long questionnaireid;
 
  private  String backcolor;
  private  Long ispublic;
  private  String name;
  private  Byte typeid;

  
   // 正确答案结果  ID　　关联  surviveanser 表
  private  String  subjectanswer;
  
  // 是否 必答
  private Byte mustanswer;
  
  // 是否匿名
  private  Byte anonymous;
  // 是否抓发
  private Byte forward;
  
  private Byte repeatedanswer;
  
  private  String answercount;
  
  private  Byte questionnairedesc;
  
  private   String  icon;
  
  private   String  cover;
  


  private  String title;
  
  
   
  private  String choicetext;
  
  public Integer getGradecount() {
    return gradecount;
  }

  public void setGradecount(Integer gradecount) {
    this.gradecount = gradecount;
  }

  public String getEverygradescore() {
    return everygradescore;
  }

  public void setEverygradescore(String everygradescore) {
    this.everygradescore = everygradescore;
  }
  private  Integer   gradecount;
  private  String    everygradescore;
  
  public List<Map<String, Object>> getList() {
    return list;
  }

  public void setList(List<Map<String, Object>> list) {
    this.list = list;
  }
  
  private   Map<String,Object>    resultinfo ;
 
  public Map<String, Object> getResultinfo() {
    return resultinfo;
  }

  public void setResultinfo(Map<String, Object> resultinfo) {
    this.resultinfo = resultinfo;
  }
  private  List<Map<String,Object>> list;
  
  
   
  
  
 
  
}
