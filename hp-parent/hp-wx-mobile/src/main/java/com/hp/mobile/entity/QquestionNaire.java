package com.hp.mobile.entity;

import java.util.Date;

public class QquestionNaire {
  private Long objectId;

  private Date createTime;

  private String createUser;

  private Date updateTime;

  private String updateUser;

  private Byte deleted;

  private Byte anonymous;

  private Byte forward;

  private Byte repeatedAnswer;

  private Short answerCount;

  private String title;

  private String questionnaireDesc;

  private String icon;

  private String cover;

  private Byte enable;

  private String backColor;

  private Byte questionnairestylestatus;
  private Byte endisshow;
  private String endimage;
  private String endimagedesc;

  private Byte coverisshow;


  public Byte getCoverisshow() {
    return coverisshow;
  }

  public void setCoverisshow(Byte coverisshow) {
    this.coverisshow = coverisshow;
  }

  public Byte getEndisshow() {
    return endisshow;
  }

  public void setEndisshow(Byte endisshow) {
    this.endisshow = endisshow;
  }


  public String getEndimage() {
    return endimage;
  }

  public void setEndimage(String endimage) {
    this.endimage = endimage;
  }

  public String getEndimagedesc() {
    return endimagedesc;
  }

  public void setEndimagedesc(String endimagedesc) {
    this.endimagedesc = endimagedesc;
  }



  public Byte getQuestionnairestylestatus() {
    return questionnairestylestatus;
  }

  public void setQuestionnairestylestatus(Byte questionnairestylestatus) {
    this.questionnairestylestatus = questionnairestylestatus;
  }

  public Short getAnswerpersoncount() {
    return answerpersoncount;
  }

  public void setAnswerpersoncount(Short answerpersoncount) {
    this.answerpersoncount = answerpersoncount;
  }

  private Short answerpersoncount;

  public Long getIsPubic() {
    return isPubic;
  }

  public void setIsPubic(Long isPubic) {
    this.isPubic = isPubic;
  }

  private Long ispublic;

  public Long getIspublic() {
    return isPubic;
  }

  public void setIspublic(Long ispublic) {
    this.isPubic = ispublic;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  private Long isPubic;
  private Date startTime;
  private Date endTime;

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

  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser == null ? null : createUser.trim();
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser == null ? null : updateUser.trim();
  }

  public Byte getDeleted() {
    return deleted;
  }

  public void setDeleted(Byte deleted) {
    this.deleted = deleted;
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

  public Byte getRepeatedAnswer() {
    return repeatedAnswer;
  }

  public void setRepeatedAnswer(Byte repeatedAnswer) {
    this.repeatedAnswer = repeatedAnswer;
  }

  public Short getAnswerCount() {
    return answerCount;
  }

  public void setAnswerCount(Short answerCount) {
    this.answerCount = answerCount;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title == null ? null : title.trim();
  }

  public String getQuestionnaireDesc() {
    return questionnaireDesc;
  }

  public void setQuestionnaireDesc(String questionnaireDesc) {
    this.questionnaireDesc = questionnaireDesc == null ? null : questionnaireDesc.trim();
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon == null ? null : icon.trim();
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover == null ? null : cover.trim();
  }

  public Byte getEnable() {
    return enable;
  }

  public void setEnable(Byte enable) {
    this.enable = enable;
  }

  public String getBackColor() {
    return backColor;
  }

  public void setBackColor(String backColor) {
    this.backColor = backColor == null ? null : backColor.trim();
  }
}
