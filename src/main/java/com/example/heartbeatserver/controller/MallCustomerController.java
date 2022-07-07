package com.example.heartbeatserver.controller;


import com.example.heartbeatserver.entity.MallCustomer;
import com.example.heartbeatserver.service.MallCustomerService;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
public class MallCustomerController {
    @Autowired
    private MallCustomerService mallCustomerService;


    @GetMapping("/{id}")
    public Result getCustomerInfo(@PathVariable Integer id){
        MallCustomer mallCustomer = mallCustomerService.getById(id);
        Result res;
        if(mallCustomer == null){
            res = ResultGenerator.genFailResult("This id is not exist");
        }else{
            res = ResultGenerator.genSuccessResultData(mallCustomer);
        }
        return res;
    }
}
