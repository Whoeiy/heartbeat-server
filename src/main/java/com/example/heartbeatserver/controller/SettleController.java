package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.GiftIdListParam;
import com.example.heartbeatserver.controller.vo.CartVo;
import com.example.heartbeatserver.controller.vo.NormalServiceVo;
import com.example.heartbeatserver.service.Impl.CartServiceImpl;
import com.example.heartbeatserver.service.Impl.SettleServiceImpl;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/a")
public class SettleController {

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private SettleServiceImpl settleService;

    @GetMapping("/settle")
    @ApiOperation("/查询生成订单列表")
    public Result<CartVo> settle(@RequestParam String giftIdList, Integer customerId) {
        String[] ids_str  = giftIdList.split(",");
        Integer[] ids = new Integer[ids_str.length];

        for (int i = 0; i < ids_str.length; i++) {
            ids[i] = Integer.parseInt(ids_str[i]);
        }

        CartVo cartVo = this.cartService.settle(customerId, ids);
        if (cartVo != null) {
            return ResultGenerator.genSuccessResultData(cartVo);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    @GetMapping("/{giftId}/service")
    @ApiOperation("/查询为该礼物提供的普通定制服务")
    public Result<List<NormalServiceVo>> getNormalServiceList(@PathVariable Integer giftId) {
        List<NormalServiceVo> normalServiceVoList = this.settleService.getNormalServiceList(giftId);
        if (normalServiceVoList != null) {
            return ResultGenerator.genSuccessResultData(normalServiceVoList);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

}
