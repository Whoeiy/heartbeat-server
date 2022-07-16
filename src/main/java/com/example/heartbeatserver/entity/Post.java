package com.example.heartbeatserver.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Post {
    private Integer postId;

    private Integer postUser;

    private String imgUrl;

    private String title;

    private String postContent;

    private Integer activityId;

    private Integer isDeleted;

    private Date createTime;

    private Date updateTime;
}
