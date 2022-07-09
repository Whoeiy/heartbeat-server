package com.example.heartbeatserver.controller.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class OrderItemVo {
    private Integer orderItemId;

    private Integer giftId;

    private String giftName;

    private Integer giftCount;

    private String giftImg;

    private Double giftPrice;

    private Double sellingPrice;

    private JSONObject service;
}
