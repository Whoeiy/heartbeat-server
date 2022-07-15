package com.example.heartbeatserver.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Activity {

    private Integer activityId;

    private String activityName;

    private String activityDetail;

    private String posterUrl;

    private Integer activityType;

    private Integer activityStatus;

    private Date startTime;

    private Date endTime;

    private Integer couponId;

    private Integer showRank;

    private Integer isShown;

    private Integer createUser;

    private Date createTime;

    private Integer isDeleted;

    private Date updateTime;

}
