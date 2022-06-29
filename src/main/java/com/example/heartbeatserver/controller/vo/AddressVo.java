package com.example.heartbeatserver.controller.vo;

import lombok.Data;


@Data
public class AddressVo {

    private Integer addressId;

    private Integer customerId;

    private String customerName;

    private String customerPhone;

    private String provinceName;

    private String cityName;

    private String regionName;

    private String detailAddress;

    private Integer defaultFlag;
}
