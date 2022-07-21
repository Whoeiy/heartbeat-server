package com.example.heartbeatserver.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class RankVo {

    private Integer activityId;

    private Integer customerRank;

    private Integer activityStatus;

    private Integer couponId;

    private List<RankItemVo> items;
}
