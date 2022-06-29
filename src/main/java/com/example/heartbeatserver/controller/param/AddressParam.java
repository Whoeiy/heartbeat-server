package com.example.heartbeatserver.controller.param;

import com.example.heartbeatserver.annotations.Phone;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddressParam {

    private Integer addressId;

    @NotEmpty(message = "收件人姓名不能为空")
    private String customerName;

    @NotEmpty(message = "收件人手机号不能为空")
    @Phone(message = "手机号格式不正确")
    private String customerPhone;

    @NotEmpty(message = "省份不能为空")
    private String provinceName;

    @NotEmpty(message = "城市不能为空")
    private String cityName;

    @NotEmpty(message = "区域不能为空")
    private String regionName;

    @NotEmpty(message = "详细地址不能为空")
    private String detailAddress;

    @NotEmpty(message = "是否为默认地址不能为空")
    private Integer defaultFlag;
}
