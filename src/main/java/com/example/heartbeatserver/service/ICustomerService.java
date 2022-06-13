package com.example.heartbeatserver.service;

import com.example.heartbeatserver.entity.Customer;

public interface ICustomerService {

    // 新用户注册
    String customerRegister(Customer customer);

    // 根据手机号查询用户信息
    Customer getCustomerByPhone(String phone);

    // 用户登录
    String customerLogin(String phone, String passwordMd5);

    // 用户登出
    String customerLogout(Integer customerId);
}
