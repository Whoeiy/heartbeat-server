package com.example.heartbeatserver.util;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Result<T> implements Serializable {

    // 业务码
    @ApiModelProperty("业务码")
    private int code;
    // 返回信息
    @ApiModelProperty("返回信息")
    private String message;
    // 数据结果
    @ApiModelProperty("数据结果")
    private T data;

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
