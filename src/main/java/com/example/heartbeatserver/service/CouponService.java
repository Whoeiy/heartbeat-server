package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.vo.CustomerCouponVo;

import java.util.List;

public interface CouponService {

    // 发放优惠券
    String sendCouponToUser(Integer couponId, Integer channel, Integer customerId);

    // 查询用户优惠券列表
    List<CustomerCouponVo> getCustomerCouponList(Integer customerId);
}
