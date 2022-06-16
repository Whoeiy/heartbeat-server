package com.example.heartbeatserver.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CarouselVo {
    @ApiModelProperty("轮播图url")
    private String imgUrl;
    @ApiModelProperty("跳转url")
    private String jumpUrl;
}
