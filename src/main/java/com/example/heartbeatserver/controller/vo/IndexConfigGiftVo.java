package com.example.heartbeatserver.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexConfigGiftVo {

    @ApiModelProperty("礼物封面图片")
    private String giftCoverImg;

    @ApiModelProperty("礼物Id")
    private Integer giftId;

    @ApiModelProperty("礼物配置名称")
    private String giftName;

    @ApiModelProperty("礼物原价")
    private Double originalPrice;

}
