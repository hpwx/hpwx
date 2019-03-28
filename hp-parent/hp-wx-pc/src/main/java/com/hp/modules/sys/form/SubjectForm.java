package com.hp.modules.sys.form;

//        questionObj: {
//                type: 1,
//                questionnaire: -1, //问卷id
//                questionRadioItems: [''],
//                questionCheckboxItems: [''],
//                starNum: 5,
//                starScore: 5,
//                isRequired: 1,
//                inputAnswer: "", //填空答案
//                radioAnswer: "",  //单选答案
//                checkBoxAnswer: [] //多选答案
//                },


public class SubjectForm {

    private int type;

    private Long questionnaireId;

    private String[] questionRadioItems;

    private String[] questionCheckboxItems;

    private Integer starNum;

    private Integer starScore;

    private Byte isRequired;

    private String inputAnswer;

    private String radioAnswer;

    private String[] checkBoxAnswer;

    private String subjectName;

    private String subjectId;

    private String nameImage;

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String[] getQuestionRadioItems() {
        return questionRadioItems;
    }

    public void setQuestionRadioItems(String[] questionRadioItems) {
        this.questionRadioItems = questionRadioItems;
    }

    public String[] getQuestionCheckboxItems() {
        return questionCheckboxItems;
    }

    public void setQuestionCheckboxItems(String[] questionCheckboxItems) {
        this.questionCheckboxItems = questionCheckboxItems;
    }

    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
    }

    public Integer getStarScore() {
        return starScore;
    }

    public void setStarScore(Integer starScore) {
        this.starScore = starScore;
    }

    public Byte getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Byte isRequired) {
        this.isRequired = isRequired;
    }

    public String getInputAnswer() {
        return inputAnswer;
    }

    public void setInputAnswer(String inputAnswer) {
        this.inputAnswer = inputAnswer;
    }

    public String getRadioAnswer() {
        return radioAnswer;
    }

    public void setRadioAnswer(String radioAnswer) {
        this.radioAnswer = radioAnswer;
    }

    public String[] getCheckBoxAnswer() {
        return checkBoxAnswer;
    }

    public void setCheckBoxAnswer(String[] checkBoxAnswer) {
        this.checkBoxAnswer = checkBoxAnswer;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
