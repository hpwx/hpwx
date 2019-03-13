package com.hp.mobile.entity;

import java.util.Date;

public class UserAnswer {
    private Long objectId;

  private Date createTime;

  private Long userid;

  private Long questionnaireId;

  private Byte deleted;
  
 

  private String openid;

  private String subjectids;
  
  public String getQustionNaireName() {
    return qustionNaireName;
  }

  public void setQustionNaireName(String qustionNaireName) {
    this.qustionNaireName = qustionNaireName;
  }

  private String  qustionNaireName;

  public String getAnswerSerialNum() {
    return answerSerialNum;
  }

  public void setAnswerSerialNum(String answerSerialNum) {
    this.answerSerialNum = answerSerialNum;
  }

  private String  answerSerialNum;

  public Long getObjectId() {
    return objectId;
  }

  public void setObjectId(Long objectId) {
    this.objectId = objectId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getUserid() {
    return userid;
  }

  public void setUserid(Long userid) {
    this.userid = userid;
  }

  public Long getQuestionnaireId() {
    return questionnaireId;
  }

  public void setQuestionnaireId(Long questionnaireId) {
    this.questionnaireId = questionnaireId;
  }

  public Byte getDeleted() {
    return deleted;
  }

  public void setDeleted(Byte deleted) {
    this.deleted = deleted;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid == null ? null : openid.trim();
  }

  public String getSubjectids() {
    return subjectids;
  }

  public void setSubjectids(String subjectids) {
    this.subjectids = subjectids == null ? null : subjectids.trim();
  }

    
}