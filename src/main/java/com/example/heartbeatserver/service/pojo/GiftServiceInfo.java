package com.example.heartbeatserver.service.pojo;

import com.example.heartbeatserver.annotations.Phone;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GiftServiceInfo {

    @NotEmpty(message = "选择服务类型不能为空")
    private Integer serviceChosenType;

    private String serviceChosenTypeName;

    private Integer normalServiceType;

    private String normalServiceTypeName;

    private Integer normalServiceId;

    private String normalServiceName;

    private String serviceNote;

    @Phone
    private String phone;
}
