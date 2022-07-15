package com.example.heartbeatserver.controller.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CouponVo {

    private Integer couponId;

    private String couponName;

    private Integer couponType;

    private Double couponPrice;

    private Date startTime;

    private Date endTime;

    private Integer showRank;

    private Integer isShown;
}
