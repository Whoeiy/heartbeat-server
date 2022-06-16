package com.example.heartbeatserver.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexLabelVo {

    @ApiModelProperty("标签Id")
    private Integer labelId;

    @ApiModelProperty("标签名称")
    private String labelName;

    @ApiModelProperty("标签等级")
    private Integer labelLevel;
}
