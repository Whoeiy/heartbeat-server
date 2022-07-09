package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.controller.vo.OrderVo;
import com.example.heartbeatserver.entity.Order;
import com.example.heartbeatserver.entity.OrderItem;
import com.example.heartbeatserver.service.pojo.OrderPayInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {

    // 新增订单
    Integer insertOrder(Order order);

    // 新增订单Item
    Integer insertBatchOrderItem(List<OrderItem> orderItems);

    // 根据订单编号查询订单
    Order getOrderByNo(String orderNo);

    // 更新订单的支付信息
    Integer updateOrderPayInfo(OrderPayInfo info);

    // 更新订单的状态
    Integer updateOrderStatus(String orderNo, Integer orderStatus);

    // 查询订单列表
    List<Order> getOrderList(Integer customerId, Integer orderStatus);

    // 查询订单Item列表
    List<OrderItem> getOrderItemList(String orderNo);
}
