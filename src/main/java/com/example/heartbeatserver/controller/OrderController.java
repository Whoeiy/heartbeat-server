package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.SaveOrderParam;
import com.example.heartbeatserver.controller.vo.OrderVo;
import com.example.heartbeatserver.service.Impl.CartServiceImpl;
import com.example.heartbeatserver.service.Impl.OrderServiceImpl;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;
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

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/saveOrder")
    @ApiOperation("/生成订单")
    public Result<String> saveOrder(Integer customerId, @RequestBody SaveOrderParam param) {
        String[] ids_str = param.getGiftIdList().split(",");
        Integer[] ids = new Integer[ids_str.length];

        for (int i = 0; i < ids_str.length; i++) {
            ids[i] = Integer.parseInt(ids_str[i]);
        }

        String res  = this.cartService.saveOrder(customerId, ids, param.getAddressId());
        String[] resArr = res.split(",");
        if (resArr[0].equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResultData(resArr[1]);
        }
        return ResultGenerator.genFailResult(res);
    }

    @GetMapping("/payOrder")
    @ApiOperation("/更新订单支付信息和状态")
    public Result<String> payOrder(@RequestParam String orderNo, @RequestParam Integer payType) {
        String res = this.orderService.payOrder(orderNo, payType);
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult(res);
        }
        return ResultGenerator.genFailResult(res);
    }

    @GetMapping("/order/list")
    @ApiOperation("/查询订单列表")
    public Result<PageResult> getOrderList(Integer customerId, @RequestParam Integer pageNum, @RequestParam Integer pageSize, Integer orderStatus) {
        PageParam param = new PageParam(pageNum, pageSize);
        PageResult res = this.orderService.getOrderList(customerId, param, orderStatus);
        return ResultGenerator.genSuccessResultData(res);
    }

    @GetMapping("/order/{orderNo}")
    @ApiOperation("/查询订单详情")
    public Result getOrderDetail(@PathVariable String orderNo) {
        OrderVo orderVo = this.orderService.getOrderDetail(orderNo);
        if (orderVo != null) {
            return ResultGenerator.genSuccessResultData(orderVo);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    @PutMapping("/order/{orderNo}/cancel")
    @ApiOperation("/取消订单")
    public Result<String> cancelOrder(@PathVariable String orderNo) {
        String res = this.orderService.cancelOrder(orderNo);
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult(res);
        }
        return ResultGenerator.genFailResult(res);
    }
}
