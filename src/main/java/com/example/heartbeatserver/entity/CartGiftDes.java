package com.example.heartbeatserver.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartGiftDes implements Serializable {
    private Integer giftId;

    private String giftName;

    private String imgUrl;
}
