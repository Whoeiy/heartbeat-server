package com.example.heartbeatserver.controller.vo;

import lombok.Data;

@Data
public class CartItemVo {

    private Integer giftId;

    private String giftName;

    private Double price;

    private String giftImg;

    private Integer count;
}
