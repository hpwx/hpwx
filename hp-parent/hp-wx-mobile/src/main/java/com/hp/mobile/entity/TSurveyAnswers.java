package com.hp.mobile.entity;

 

import java.util.Date;

 
public class TSurveyAnswers {

   
    private Long objectId;

    private Long questionId;

    private String choiceText;

    private Integer poll;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Byte deleted;
    
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getTypeid() {
      return typeid;
    }

    public void setTypeid(String typeid) {
      this.typeid = typeid;
    }

    private   String name ;
    private  String typeid;
    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText == null ? null : choiceText.trim();
    }

    public Integer getPoll() {
        return poll;
    }

    public void setPoll(Integer poll) {
        this.poll = poll;
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
}