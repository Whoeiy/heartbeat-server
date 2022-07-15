package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.CouponSendParam;
import com.example.heartbeatserver.controller.vo.CustomerCouponVo;
import com.example.heartbeatserver.service.Impl.CouponServiceImpl;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/a/coupon")
public class CouponController {

    @Autowired
    private CouponServiceImpl couponService;

    @PostMapping("/send")
    @ApiOperation("/发放优惠券")
    public Result sendCoupon(@RequestBody CouponSendParam param, Integer customerId) {
        String res = this.couponService.sendCouponToUser(param.getCouponId(), param.getChannel(), customerId);
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult(res);
    }

    @GetMapping("/list")
    @ApiOperation("/查询用户我的优惠券")
    public Result getCouponList(Integer customerId) {
        List<CustomerCouponVo> customerCouponVoList = this.couponService.getCustomerCouponList(customerId);
        return ResultGenerator.genSuccessResultData(customerCouponVoList);
    }
}
