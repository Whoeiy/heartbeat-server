package com.example.heartbeatserver.common;

public enum CartEnum {

    SHOP_CART_GIFT_COUNT_FULL(1000, "购物车已满，不能添加新礼物"),

    GIFT_NOT_EXISTS(10100, "数据库不存在此礼物"),

    GIFT_IS_OFF_THE_SHELVES(10101, "礼物已下架"),

    UPDATE_GIFT_DES_IN_REDIS_SUCCESS("Gift description is update-to-date"),

    ;


    private Integer code;

    private String message;

    CartEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    CartEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
