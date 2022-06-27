package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.param.AddGiftIntoCartParam;
import com.example.heartbeatserver.controller.vo.CartVo;
import com.example.heartbeatserver.entity.Cart;
import com.example.heartbeatserver.entity.Gift;

public interface CartService {
    // 加入礼物到购物车
    String addGiftIntoCart(Integer customerId, AddGiftIntoCartParam param);

    // 显示购物车
    Cart showCart(Integer customerId);

    // 显示购物车展示页
    CartVo showCartVo(Integer customerId);

    // 更新礼物描述缓存信息
    String updateGiftDesRedis();

}
