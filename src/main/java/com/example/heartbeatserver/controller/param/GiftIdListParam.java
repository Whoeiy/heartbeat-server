package com.example.heartbeatserver.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GiftIdListParam {

    @NotEmpty(message = "礼物Id列表不能为空")
    private String giftIdList;
}
