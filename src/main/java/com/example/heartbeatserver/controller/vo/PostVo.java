package com.example.heartbeatserver.controller.vo;

import lombok.Data;

@Data
public class PostVo {

    private Integer postId;

    private Integer postUser;

    private String customerName;

    private String imgUrl;

    private String title;

    private String postContent;

    private Integer activityId;

    private Integer likeCount;

    private Integer isLiked;
}
