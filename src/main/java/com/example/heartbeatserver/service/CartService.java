package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.param.AddGiftIntoCartParam;
import com.example.heartbeatserver.controller.vo.CartVo;
import com.example.heartbeatserver.entity.Cart;
import com.example.heartbeatserver.entity.Gift;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;

public interface CartService {
    // 加入礼物到购物车
    String addGiftIntoCart(Integer customerId, AddGiftIntoCartParam param);

    // 显示购物车
    Cart showCart(Integer customerId);

    // 显示购物车展示页
    CartVo showCartVo(Integer customerId);

    // 更新礼物描述缓存信息
    String updateGiftDesRedis();

    // 删除购物车中的礼物
    String deleteGiftInCart(Integer customerId, Integer giftId);

    // 生成确认订单
    CartVo settle(Integer customerId, Integer[] giftIds);

    // 购买生成订单
     String saveOrder(Integer customerId, Integer[] giftIds, Integer addressId);

}
