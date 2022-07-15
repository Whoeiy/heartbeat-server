package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponDao {

    // 查询优惠券详情
    Coupon getCouponInfo(Integer couponId);
}
