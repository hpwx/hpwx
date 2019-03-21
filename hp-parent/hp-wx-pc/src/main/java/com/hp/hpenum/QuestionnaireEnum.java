package com.hp.hpenum;

public enum QuestionnaireEnum {

    UP(1,"up"),
    DOWN(0,"down"),
    ;
    private int code;

    private String msg;

    QuestionnaireEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
