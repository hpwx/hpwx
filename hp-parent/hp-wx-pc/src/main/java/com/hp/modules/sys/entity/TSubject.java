package com.hp.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hp.common.validator.group.AddGroup;
import com.hp.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@TableName("t_subject")
public class TSubject implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId
    private Long objectId;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Byte deleted;

    @NotBlank(message="不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    @NotNull(message="问题类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer typeId;

    private String subjectAnswer;

    private Integer gradeCount;

    private String everyGradeScore;

    public Integer getGradeCount() {
        return gradeCount;
    }

    public void setGradeCount(Integer gradeCount) {
        this.gradeCount = gradeCount;
    }

    public String getEveryGradeScore() {
        return everyGradeScore;
    }

    public void setEveryGradeScore(String everyGradeScore) {
        this.everyGradeScore = everyGradeScore;
    }

    //    @Transient
//    private String tQuestionnaireName;

//    @Transient
//    private Long tQuestionnaireId;

//    @Transient
//    private Byte mustAnswer;

//    public Byte getMustAnswer() {
//        return mustAnswer;
//    }
//
//    public void setMustAnswer(Byte mustAnswer) {
//        this.mustAnswer = mustAnswer;
//    }

//    public Long gettQuestionnaireId() {
//        return tQuestionnaireId;
//    }
//
//    public void settQuestionnaireId(Long tQuestionnaireId) {
//        this.tQuestionnaireId = tQuestionnaireId;
//    }

//    public String gettQuestionnaireName() {
//        return tQuestionnaireName;
//    }
//
//    public void settQuestionnaireName(String tQuestionnaireName) {
//        this.tQuestionnaireName = tQuestionnaireName;
//    }

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