package com.hp.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("t_questionnaire")
public class TQuestionnaire implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId
    private Long objectId;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private int deleted;

    private int anonymous;

    private int forward;

    private int top;

    private int repeatedAnswer;

    private int answerCount;

    private String title;

    private String questionnaireDesc;

    private String icon;

    private String cover;

    private int enable;

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
        this.createUser = createUser;
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
        this.updateUser = updateUser;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public int getForward() {
        return forward;
    }

    public void setForward(int forward) {
        this.forward = forward;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRepeatedAnswer() {
        return repeatedAnswer;
    }

    public void setRepeatedAnswer(int repeatedAnswer) {
        this.repeatedAnswer = repeatedAnswer;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionnaireDesc() {
        return questionnaireDesc;
    }

    public void setQuestionnaireDesc(String questionnaireDesc) {
        this.questionnaireDesc = questionnaireDesc;
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

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "TQuestionnaire{" +
                "objectId=" + objectId +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                ", deleted=" + deleted +
                ", anonymous=" + anonymous +
                ", forward=" + forward +
                ", top=" + top +
                ", repeatedAnswer=" + repeatedAnswer +
                ", answerCount=" + answerCount +
                ", title='" + title + '\'' +
                ", questionnaireDesc='" + questionnaireDesc + '\'' +
                ", icon='" + icon + '\'' +
                ", cover='" + cover + '\'' +
                ", enable=" + enable +
                '}';
    }
}
