package com.example.heartbeatserver.controller.param;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class InfoParam {
    @NotEmpty(message = "用户名不能为空")
    private String customerName;

    @Email(message = "邮箱地址格式不正确")
    private String email;

    @NotEmpty(message = "原密码不能为空")
    private String orgPasswordMd5;

    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

}
