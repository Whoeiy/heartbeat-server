package com.example.heartbeatserver.controller.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderVo {

    private String orderNo;

    private String address;

    private Integer orderStatus;

    private String orderStatusName;

    private Integer payStatus;

    private Integer payType;

    private Date payTime;

    private Double totalPrice;

    private Date createTime;

    private List<OrderItemVo> orderItemList;

}
