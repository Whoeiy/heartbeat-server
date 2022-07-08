package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.vo.CartVo;
import com.example.heartbeatserver.entity.Address;
import com.example.heartbeatserver.entity.Cart;

public interface OrderService {

//    CartVo settle()
    String createOrder(Cart buyCart, Integer addressId);

}
