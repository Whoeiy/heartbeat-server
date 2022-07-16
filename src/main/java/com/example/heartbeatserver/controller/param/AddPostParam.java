package com.example.heartbeatserver.controller.param;

import lombok.Data;

@Data
public class AddPostParam {

    private String imgUrl;

    private String title;

    private String postContent;

    private Integer activityId;
}
