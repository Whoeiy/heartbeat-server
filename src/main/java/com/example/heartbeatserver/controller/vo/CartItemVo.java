package com.example.heartbeatserver.controller.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class CartItemVo {

    private Integer giftId;

    private String giftName;

    private Double price;

    private String giftImg;

    private JSONObject service;

    private Double sellingPrice;

    private Integer count;
}
