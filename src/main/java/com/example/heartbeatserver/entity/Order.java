package com.example.heartbeatserver.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Order {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户Id
     */
    private Integer customerId;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 订单类型
     * 1 - 礼物订单
     * 2 - 其他订单
     */
    private Integer orderType;

    /**
     * 订单状态
     * 0 - 待支付
     * 1 - 待定制
     * 2 - 定制中
     * 3 - 定制完成
     * 4 - 待发货
     * 5 - 已发货/出库成功
     * 6 - 交易完成/手动关闭
     */
    private Integer orderStatus;

    /**
     * 支付状态
     * 0 - 未支付
     * 1 - 已支付
     */
    private Integer payStatus;

    /**
     * 支付方式
     * 0 - 微信
     * 1 - 支付宝
     */
    private Integer payType;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 总金额
     */
    private Double totalPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
