package com.example.heartbeatserver.entity;

import com.example.heartbeatserver.annotations.Phone;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class Address {
    private Integer addressId;

    private Integer customerId;

    private String customerName;

    @NotEmpty(message = "手机号不能为空")
    @Phone(message = "手机号格式不正确")
    private String customerPhone;

    private String provinceName;

    private String cityName;

    private String regionName;

    private String detailAddress;

    private Integer defaultFlag;

    private Integer isDeleted;

    private Date createTime;

    private Date updateTime;
}
