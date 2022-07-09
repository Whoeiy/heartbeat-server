package com.example.heartbeatserver.service.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderPayInfo {

    private String orderNo;

    private Integer payStatus;

    private Integer payType;

    private Date payTime;

}
