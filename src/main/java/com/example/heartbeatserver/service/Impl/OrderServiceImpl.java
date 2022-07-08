package com.example.heartbeatserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.dao.AddressDao;
import com.example.heartbeatserver.dao.OrderDao;
import com.example.heartbeatserver.entity.*;
import com.example.heartbeatserver.service.OrderService;
import com.example.heartbeatserver.util.PublicData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private AddressDao addressDao;

    @Override
    @Transactional
    public String createOrder(Cart buyCart, Integer addressId) {
        Order order = new Order();
        // 生成订单编号
        order.setOrderNo(this.generateOrderNo());
        // 用户id
        order.setCustomerId(buyCart.getCustomerId());
        // 订单类型: 默认是礼物订单
        order.setOrderType(1);
        // 订单状态: 待支付
        order.setOrderStatus(0);
        // 支付状态：未支付
        order.setPayStatus(0);
        // 创建时间
        order.setCreateTime(new Date());
        // 收获地址
        Address address = this.addressDao.getAddress(addressId);
        String orderAddress = address.getCustomerName() + ", " + address.getCustomerPhone()
                + ", " + address.getProvinceName() + ", " + address.getCityName()
                + ", " + address.getRegionName() + ", " + address.getDetailAddress();
        order.setAddress(orderAddress);

        Double sumPrice = 0.0;
        List<CartItem> cartItemList = buyCart.getCartItemList();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < cartItemList.size(); i++) {
            CartItem cartItem = cartItemList.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setGiftId(cartItem.getGiftId());
            orderItem.setOrderNo(order.getOrderNo());
            // 获得礼物的描述信息
            String itemName = PublicData.GIFT_DES_REDIS_ITEM_NAME + cartItem.getGiftId();
            Object obj = redisTemplate.opsForHash().get(PublicData.GIFT_DES_REDIS_KEY, itemName);
            if (obj != null) {
                CartGiftDes cartGiftDes = JSON.parseObject(obj.toString(), CartGiftDes.class);
                orderItem.setGiftName(cartGiftDes.getGiftName());
                orderItem.setGiftImg(cartGiftDes.getImgUrl());
            } else {
                return ServiceResultEnum.DATA_NOT_EXIST.getResult() + " - redis中不存在该礼物的描述信息";
            }
            orderItem.setGiftCount(cartItem.getCount());
            orderItem.setGiftPrice(cartItem.getPrice());
            // 计算小计金额
            Double sellingPrice = cartItem.getPrice() * cartItem.getCount();
            orderItem.setSellingPrice(sellingPrice);
            orderItem.setService(cartItem.getService());
            // 加入到list中
            orderItemList.add(orderItem);
            sumPrice += sellingPrice;
        }

        // 订单总额
        order.setTotalPrice(sumPrice);
        Integer resOrder = this.orderDao.insertOrder(order);
        Integer resOrderItem = this.orderDao.insertBatchOrderItem(orderItemList);
        if(resOrder > 0 && resOrderItem > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    public String generateOrderNo() {
        // 生成订单编号
        StringBuilder stringBuilder = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

        String key = PublicData.ORDER_REDIS_KEY + ":" + PublicData.ORDER_REDIS_ITEM_NAME + date;
        Long increment = incr(key, 1);
        stringBuilder.append(date);
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            stringBuilder.append(String.format("%06d", increment));
        } else {
            stringBuilder.append(incrementStr);
        }
        return stringBuilder.toString();
    }

    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }
}
