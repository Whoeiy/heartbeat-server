package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerDao {

    // 根据手机号查询用户信息
    Customer getCustomerByPhone(String phone);

    // 新用户注册
    Integer addCustomer(Customer customer);
}
