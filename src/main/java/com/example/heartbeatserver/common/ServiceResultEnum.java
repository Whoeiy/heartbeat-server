package com.example.heartbeatserver.common;

public enum ServiceResultEnum {

    SUCCESS("操作成功"),

    DATA_NOT_EXIST("未查询到记录"),

    DB_ERROR("数据库错误"),

    PHONE_PSWD_INCORRECT("手机号或密码错误");


    private String result;

    ServiceResultEnum(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}