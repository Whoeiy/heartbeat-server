package com.example.heartbeatserver.controller.param;

import com.example.heartbeatserver.annotations.Phone;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GiftServiceParam {

    @NotEmpty(message = "礼物Id不能为空")
    private Integer giftId;

    @NotEmpty(message = "选择服务类型不能为空")
    private Integer serviceChosenType;

    private Integer normalServiceId;

    private String serviceNote;

    @Phone
    private String phone;
}
