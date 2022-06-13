package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.Customer;
import com.example.heartbeatserver.entity.CustomerToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerDao {

    // 根据手机号查询用户信息
    Customer getCustomerByPhone(String phone);

    // 新用户注册
    Integer addCustomer(Customer customer);

    // 根据手机号查询用户id
    Integer getCustomerIdByPhone(String phone);

    // 新增用户token
    Integer addCustomerToken(Integer uid, String token);

    // 查询用户token
    CustomerToken getCustomerTokenByCustomerId(Integer customerId);

    // 更新用户token
    Integer updateCustomerToken(Integer uid, String token);

}
