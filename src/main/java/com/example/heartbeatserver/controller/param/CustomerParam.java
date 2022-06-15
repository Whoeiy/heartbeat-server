package com.example.heartbeatserver.controller.param;

import com.example.heartbeatserver.annotations.Phone;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class CustomerParam {
    @NotEmpty(message = "用户名不能为空")
    // 长度限制待加
    private String name;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "手机号不能为空")
    @Phone(message = "手机号格式不正确")
    private String phone;

    @NotEmpty(message = "邮箱地址不能为空")
    @Email(message = "邮箱地址格式不正确")
    private String email;
}
