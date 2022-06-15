package com.example.heartbeatserver.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Customer {
    private Integer customerId;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String address;
    private Date createTime;
    private Date updateTime;
}
