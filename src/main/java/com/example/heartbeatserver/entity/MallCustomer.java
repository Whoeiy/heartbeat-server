package com.example.heartbeatserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName mall_customer
 */
@TableName(value ="mall_customer")
@Data
public class MallCustomer implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer customerid;

    /**
     * 用户昵称
     */
    private String customername;

    /**
     * 用户密码
     */
    private String passwordmd5;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户收货地址
     */
    private String address;

    /**
     * 
     */
    private Date createtime;

    /**
     * 
     */
    private Date updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}