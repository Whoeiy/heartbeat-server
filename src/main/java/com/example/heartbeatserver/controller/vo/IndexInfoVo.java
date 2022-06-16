package com.example.heartbeatserver.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class IndexInfoVo {
    @ApiModelProperty("轮播图List")
    private List<CarouselVo> carousels;

    @ApiModelProperty("标签list")
    private List<IndexLabelVo> labels;

    @ApiModelProperty("新品上线list")
    private List<IndexConfigGiftVo> newGifts;

    @ApiModelProperty("热门礼物list")
    private List<IndexConfigGiftVo> hotGifts;

    @ApiModelProperty("推荐礼物list")
    private List<IndexConfigGiftVo> recommendGifts;
}
