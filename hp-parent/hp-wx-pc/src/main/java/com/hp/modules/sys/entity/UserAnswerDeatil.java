package com.hp.modules.sys.entity;

import java.util.Date;

public class UserAnswerDeatil {
    private Long objectId;

    private Long tUserAnswerId;

    private Long subjectId;

    private String subjectName;

    private String answerResult;

    private Integer answerScore;

    private String correctResult;

    private Byte subjectType;

    private Date answerTime;

    private String questionnaireName;

    private Long questionnaireId;

    private String openId;

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long gettUserAnswerId() {
        return tUserAnswerId;
    }

    public void settUserAnswerId(Long tUserAnswerId) {
        this.tUserAnswerId = tUserAnswerId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    public String getAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(String answerResult) {
        this.answerResult = answerResult == null ? null : answerResult.trim();
    }

    public Integer getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(Integer answerScore) {
        this.answerScore = answerScore;
    }

    public String getCorrectResult() {
        return correctResult;
    }

    public void setCorrectResult(String correctResult) {
        this.correctResult = correctResult == null ? null : correctResult.trim();
    }

    public Byte getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Byte subjectType) {
        this.subjectType = subjectType;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName == null ? null : questionnaireName.trim();
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }
}