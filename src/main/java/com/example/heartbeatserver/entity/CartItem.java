package com.example.heartbeatserver.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Serializable {

    private Integer giftId;

    private Double price;

    private JSONObject service;

    private Integer count;
}
