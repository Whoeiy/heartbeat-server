package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.vo.CartVo;
import com.example.heartbeatserver.controller.vo.OrderVo;
import com.example.heartbeatserver.entity.Address;
import com.example.heartbeatserver.entity.Cart;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;

import java.util.List;

public interface OrderService {

    // 创建订单
    String createOrder(Cart buyCart, Integer addressId);

    // 支付订单
    String payOrder(String orderNo, Integer payType);

    // 查询订单列表
    PageResult<List<OrderVo>> getOrderList(Integer customerId, PageParam param, Integer orderStatus);

    // 查询订单详情
    OrderVo getOrderDetail(String orderNo);

    // 取消订单
    String cancelOrder(String orderNo);
}
