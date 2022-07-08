package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.Order;
import com.example.heartbeatserver.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {

    // 新增订单
    Integer insertOrder(Order order);

    // 新增订单Item
    Integer insertBatchOrderItem(List<OrderItem> orderItems);
}
