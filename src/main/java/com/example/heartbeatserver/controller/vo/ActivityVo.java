package com.example.heartbeatserver.controller.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityVo {

    private Integer activityId;

    private String activityName;

    private String activityDetail;

    private String posterUrl;

    private Integer activityType;

    private Integer activityStatus;

    private Date startTime;

    private Date endTime;

    private CouponVo coupon;

    private Integer showRank;

    private Integer isShown;

}
