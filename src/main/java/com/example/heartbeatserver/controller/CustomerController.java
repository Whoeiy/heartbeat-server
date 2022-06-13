package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.CustomerLoginParam;
import com.example.heartbeatserver.controller.param.CustomerParam;
import com.example.heartbeatserver.entity.Customer;
import com.example.heartbeatserver.service.ICustomerService;
import com.example.heartbeatserver.service.Impl.CustomerServiceImpl;
import com.example.heartbeatserver.util.BeanUtil;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/a/user")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/register")
    @ApiOperation("/新用户注册")
    public Result<String> customerRegister(@RequestBody CustomerParam customerParam) {
        Customer customer = this.customerService.getCustomerByPhone(customerParam.getPhone());
        if (customer != null) {
            return ResultGenerator.genFailResult("该手机号已注册");
        }
        customer = new Customer();
        BeanUtil.copyProperties(customerParam, customer);
        String res = this.customerService.customerRegister(customer);
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult(res);
    }

    @PostMapping("/login")
    @ApiOperation("/用户登录")
    public Result<String> customerLogin(@RequestBody CustomerLoginParam customerLoginParam) {
        String res = this.customerService.customerLogin(customerLoginParam.getPhone(), customerLoginParam.getPassword());
        if (res.equals(ServiceResultEnum.DATA_NOT_EXIST.getResult())) {
            return ResultGenerator.genFailResult("该用户未注册");
        }
        if (res.equals(ServiceResultEnum.PHONE_PSWD_INCORRECT.getResult())) {
            return ResultGenerator.genFailResult(res);
        }
        if (res.equals(ServiceResultEnum.DB_ERROR.getResult())) {
            return ResultGenerator.genFailResult(res);
        }
        return ResultGenerator.genSuccessResultData(res);
    }

    @DeleteMapping("/logout")
    @ApiOperation("/用户登出")
    public Result<String> customerLogout(Integer customerId) {
        String res = this.customerService.customerLogout(customerId);
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult("登出成功");
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DB_ERROR.getResult());
    }

}
