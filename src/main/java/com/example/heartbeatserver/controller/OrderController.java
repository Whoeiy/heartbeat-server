package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.SaveOrderParam;
import com.example.heartbeatserver.service.Impl.CartServiceImpl;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/a")
public class OrderController {
    @Autowired
    private CartServiceImpl cartService;

    @PostMapping("/saveOrder")
    @ApiOperation("/生成订单")
    public Result<String> saveOrder(Integer customerId, @RequestBody SaveOrderParam param) {
        String[] ids_str = param.getGiftIdList().split(",");
        Integer[] ids = new Integer[ids_str.length];

        for (int i = 0; i < ids_str.length; i++) {
            ids[i] = Integer.parseInt(ids_str[i]);
        }

        String res  = this.cartService.saveOrder(customerId, ids, param.getAddressId());
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult(res);
        }
        return ResultGenerator.genFailResult(res);
    }
}
