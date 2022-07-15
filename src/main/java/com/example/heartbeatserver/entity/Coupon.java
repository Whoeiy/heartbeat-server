package com.example.heartbeatserver.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Coupon {

    private Integer couponId;

    private String couponName;

    private Integer couponType;

    private Double couponPrice;

    private Date startTime;

    private Date endTime;

    private Integer showRank;

    private Integer isShown;

    private Integer createUser;

    private Integer isDeleted;

    private Date createTime;

    private Date updateTime;

}
