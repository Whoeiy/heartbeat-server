package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.Coupon;
import com.example.heartbeatserver.entity.CustomerCoupon;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CouponDao {

    // 查询优惠券详情
    Coupon getCouponInfo(Integer couponId);

    // 为某用户发放优惠券
    Integer sendCouponToUser(CustomerCoupon coupon);

    // 查询某用户的优惠券列表
    List<CustomerCoupon> getCustomerCouponList(Integer customerId, Date searchTime);
}
