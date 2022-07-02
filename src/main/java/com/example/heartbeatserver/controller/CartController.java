package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.common.CartEnum;
import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.AddGiftIntoCartParam;
import com.example.heartbeatserver.controller.vo.CartVo;
import com.example.heartbeatserver.service.Impl.CartServiceImpl;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/a/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @GetMapping
    @ApiOperation("/显示购物车")
    public Result<CartVo> showCart(Integer customerId) {
        CartVo cartVo = this.cartService.showCartVo(customerId);
        return ResultGenerator.genSuccessResultData(cartVo);
    }

    @PostMapping
    @ApiOperation("/加入购物车")
    public Result<String> addIntoCart(@RequestBody AddGiftIntoCartParam param, Integer customerId) {
        String res = this.cartService.addGiftIntoCart(customerId, param);
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult(null);
    }

    @PutMapping("/update-gift-des")
    @ApiOperation("/更新redis礼物描述信息")
    public Result<String> updateGiftDes(){
        String res = this.cartService.updateGiftDesRedis();
        if (res.equals(CartEnum.CART_DEL_GIFT_SUCCESS.getMessage())){
            return ResultGenerator.genSuccessResult(res);
        }
        return ResultGenerator.genFailResult(null);
    }

    @DeleteMapping
    @ApiOperation("/删除购物车中的礼物")
    public Result<String> deleteGiftInCart(@RequestParam Integer giftId, Integer customerId) {
        String res = this.cartService.deleteGiftInCart(customerId, giftId);
        if (res.equals(CartEnum.CART_DEL_GIFT_SUCCESS.getMessage())) {
            return ResultGenerator.genSuccessResult(res);
        }
        return ResultGenerator.genFailResult(res);
    }
}
