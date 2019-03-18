package com.hp.hpenum;

public enum SubjectEnum {

//  1：单选题 2：多选题 3：填空题  4.打分  5.选择
    SUBJECT_RADIO(1,"单选题"),
    SUBJECT_CHECKBOX(2,"多选题"),
    SUBJECT_INPUT(3,"填空题"),
    SUBJECT_STAR(4,"打分"),
    ;

    private Integer code;

    private String desc;

    SubjectEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
