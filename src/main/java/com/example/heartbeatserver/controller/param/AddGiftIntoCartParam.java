package com.example.heartbeatserver.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddGiftIntoCartParam {
    @NotEmpty(message = "礼物Id不能为空")
    private Integer giftId;

    private Integer count = 1;

    @NotEmpty(message = "礼物价格不能为空")
    private Double price;
}
