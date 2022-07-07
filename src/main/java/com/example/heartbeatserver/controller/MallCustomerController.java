package com.example.heartbeatserver.controller;


import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.InfoParam;
import com.example.heartbeatserver.controller.vo.InfoVo;
import com.example.heartbeatserver.entity.MallCustomer;
import com.example.heartbeatserver.service.MallCustomerService;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/a")
public class MallCustomerController {
    @Autowired
    private MallCustomerService mallCustomerService;


    @GetMapping("/info")
    public Result getCustomerInfo(Integer customerId){
        MallCustomer mallCustomer = mallCustomerService.getById(customerId);
        Result res;
        InfoVo infoVo = new InfoVo();
        infoVo.setCustomerName(mallCustomer.getCustomername());
        infoVo.setPhone(mallCustomer.getPhone());
        infoVo.setEmail(mallCustomer.getEmail());
        if(mallCustomer == null){
            res = ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }else{
            res = ResultGenerator.genSuccessResultData(infoVo);
        }
        return res;
    }

    @PutMapping("/info")
    public Result updateCustomerInfo(Integer customerId, @RequestBody InfoParam infoParam){
        MallCustomer mallCustomer = mallCustomerService.getById(customerId);
        String pass1 = infoParam.getOrgPasswordMd5();
        String pass2 = mallCustomer.getPasswordmd5();
        String newPassword = infoParam.getNewPassword();

        if(pass1 == null) {
            pass1 = mallCustomer.getPasswordmd5();
            pass2 = mallCustomer.getPasswordmd5();
            newPassword = mallCustomer.getPasswordmd5();
        }

       if(pass1.equals(pass2) ){
           mallCustomer.setPasswordmd5(newPassword);
           mallCustomer.setCustomername(infoParam.getCustomerName());
           mallCustomer.setEmail(infoParam.getEmail());
           mallCustomer.setUpdatetime(new Date());
           mallCustomerService.updateById(mallCustomer);
           return ResultGenerator.genSuccessResult();

       }else {
           return ResultGenerator.genFailResult("原密码不正确");
       }

    }
}
