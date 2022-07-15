package com.example.heartbeatserver.service.Impl;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.vo.CustomerCouponVo;
import com.example.heartbeatserver.dao.ActivityDao;
import com.example.heartbeatserver.dao.CouponDao;
import com.example.heartbeatserver.entity.Coupon;
import com.example.heartbeatserver.entity.CustomerCoupon;
import com.example.heartbeatserver.service.CouponService;
import com.example.heartbeatserver.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private ActivityDao activityDao;

    private Map<Integer, String> getCouponTypeNameMap() {
        Map<Integer, String> serviceMap = new HashMap<>();
        serviceMap.put(1, "活动优惠券");
        serviceMap.put(2, "普通优惠券");
        return serviceMap;
    }

    @Override
    public String sendCouponToUser(Integer couponId, Integer channel, Integer customerId) {

        CustomerCoupon customerCoupon = new CustomerCoupon();
        Coupon coupon = this.couponDao.getCouponInfo(couponId);
        if(coupon == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult() + " - 优惠券";
        }
        if(channel != 0) {
            if(this.activityDao.getActivityInfo(channel) == null){
                return ServiceResultEnum.DATA_NOT_EXIST.getResult() + " - 活动";
            }
        }
        customerCoupon.setCouponId(couponId);
        customerCoupon.setChannel(channel);
        customerCoupon.setCustomerId(customerId);
        customerCoupon.setIsUsed(0); // 未使用
        customerCoupon.setStartTime(coupon.getStartTime()); // 开始时间
        customerCoupon.setEndTime(coupon.getEndTime());
        customerCoupon.setAddTime(new Date());

        if(this.couponDao.sendCouponToUser(customerCoupon) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public List<CustomerCouponVo> getCustomerCouponList(Integer customerId) {

        List<CustomerCouponVo> couponVos = new ArrayList<>();
        List<CustomerCoupon> customerCoupons = this.couponDao.getCustomerCouponList(customerId, new Date());

        if(customerCoupons.isEmpty()){
            return couponVos;
        }

        for(CustomerCoupon customerCoupon : customerCoupons) {
            CustomerCouponVo couponVo = new CustomerCouponVo();
            BeanUtil.copyProperties(customerCoupon, couponVo);
            // 获取优惠券信息
            Coupon coupon = this.couponDao.getCouponInfo(customerCoupon.getCouponId());
            couponVo.setCouponName(coupon.getCouponName());
            couponVo.setCouponType(coupon.getCouponType());
            couponVo.setCouponTypeName(this.getCouponTypeNameMap().get(coupon.getCouponType()));
            couponVo.setCouponPrice(coupon.getCouponPrice());
            couponVos.add(couponVo);
        }
        return couponVos;
    }
}
