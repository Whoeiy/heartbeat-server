package com.example.heartbeatserver.controller.vo;

import lombok.Data;

@Data
public class RankItemVo {

    private Integer postId;

    private Integer postUser;

    private String customerName;

    private String title;

    private Integer likeCount;
}
