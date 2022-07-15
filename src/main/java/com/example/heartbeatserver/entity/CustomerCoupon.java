package com.example.heartbeatserver.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerCoupon {

    private Integer itemId;

    private Integer customerId;

    private Integer couponId;

    private Integer channel;

    private Integer isUsed;

    private Date startTime;

    private Date endTime;

    private Date addTime;

    private Date updateTime;

}
