package com.example.heartbeatserver.controller.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerCouponVo {

    private Integer itemId;

    private Integer customerId;

    private Integer couponId;

    private String couponName;

    private Integer couponType;

    private String couponTypeName;

    private Double couponPrice;

    private Integer channel;

    private Date startTime;

    private Date endTime;

    private Date addTime;

}
