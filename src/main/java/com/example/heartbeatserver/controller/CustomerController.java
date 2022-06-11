package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.controller.param.CustomerParam;
import com.example.heartbeatserver.entity.Customer;
import com.example.heartbeatserver.service.ICustomerService;
import com.example.heartbeatserver.service.Impl.CustomerServiceImpl;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mall")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping
    @ApiOperation("新用户注册")
    public Result<String> customerRegister(@RequestBody CustomerParam customerParam) {
        Customer customer = this.customerService.getCustomerByPhone(customerParam.getPhone());
        return ResultGenerator.genFailResult(null);
    }

}
