package com.example.heartbeatserver.common;

public enum ServiceResultEnum {

    SUCCESS("SUCCESS"),

    DATA_NOT_EXIST("未查询到记录！"),

    DB_ERROR("database error");


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