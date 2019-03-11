package com.hp.mobile.entity;

import java.util.Date;
import java.util.List;

public class Subject {
    private Long objectId;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Byte deleted;

    private String name;

    private Integer typeId;

    private String subjectAnswer;
    
    
   

    private  List<TSurveyAnswers> surveyanswerlist;
    
    public List<TSurveyAnswers> getSurveyanswerlist() {
      return surveyanswerlist;
    }

    public void setSurveyanswerlist(List<TSurveyAnswers> surveyanswerlist) {
      this.surveyanswerlist = surveyanswerlist;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getSubjectAnswer() {
        return subjectAnswer;
    }

    public void setSubjectAnswer(String subjectAnswer) {
        this.subjectAnswer = subjectAnswer;
    }
}