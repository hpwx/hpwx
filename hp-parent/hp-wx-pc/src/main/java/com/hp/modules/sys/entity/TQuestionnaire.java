package com.hp.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hp.common.validator.group.AddGroup;
import com.hp.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("t_questionnaire")
public class TQuestionnaire {

    @TableId
    private Long objectId;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Byte deleted;

//    @NotBlank(message="是否匿名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Byte anonymous;

//    @NotBlank(message="是否转发不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Byte forward;

//    @NotBlank(message="是否重复作答不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Byte repeatedAnswer;

    private Short answerCount;

    @NotBlank(message="标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String title;

    private String questionnaireDesc;

    //问卷图标图片
    private String icon;

    //问卷图标封面
    private String cover;

    //问卷是否启用
    private Byte enable;

    //题目背景图片
    private String backColor;

    private Byte questionnaireStyleStatus;

    private Byte isPublic;

    private Byte endIsShow;

    private Byte coverIsShow;

    private String endImage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Byte questionnaireIsTop;

    private int starNum;

    private int starScore;

    public Byte getEndIsShow() {
        return endIsShow;
    }

    public void setEndIsShow(Byte endIsShow) {
        this.endIsShow = endIsShow;
    }

    public Byte getCoverIsShow() {
        return coverIsShow;
    }

    public void setCoverIsShow(Byte coverIsShow) {
        this.coverIsShow = coverIsShow;
    }

    public String getEndImage() {
        return endImage;
    }

    public void setEndImage(String endImage) {
        this.endImage = endImage;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public int getStarScore() {
        return starScore;
    }

    public void setStarScore(int starScore) {
        this.starScore = starScore;
    }

    public Byte getQuestionnaireIsTop() {
        return questionnaireIsTop;
    }

    public void setQuestionnaireIsTop(Byte questionnaireIsTop) {
        this.questionnaireIsTop = questionnaireIsTop;
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

    public Byte getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Byte isPublic) {
        this.isPublic = isPublic;
    }

    public Byte getQuestionnaireStyleStatus() {
        return questionnaireStyleStatus;
    }

    public void setQuestionnaireStyleStatus(Byte questionnaireStyleStatus) {
        this.questionnaireStyleStatus = questionnaireStyleStatus;
    }

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