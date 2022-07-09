package com.example.heartbeatserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.vo.OrderItemVo;
import com.example.heartbeatserver.controller.vo.OrderVo;
import com.example.heartbeatserver.dao.AddressDao;
import com.example.heartbeatserver.dao.OrderDao;
import com.example.heartbeatserver.entity.*;
import com.example.heartbeatserver.service.OrderService;
import com.example.heartbeatserver.service.pojo.OrderPayInfo;
import com.example.heartbeatserver.util.BeanUtil;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;
import com.example.heartbeatserver.util.PublicData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private AddressDao addressDao;

    private Map<Integer, String> getOrderStatusNameMap() {
        Map<Integer, String> serviceMap = new HashMap<>();
        serviceMap.put(0, "待支付");
        serviceMap.put(1, "待定制");
        serviceMap.put(2, "定制中");
        serviceMap.put(3, "定制完成");
        serviceMap.put(4, "待发货");
        serviceMap.put(5, "已发货");
        serviceMap.put(6, "交易完成");
        return serviceMap;
    }

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

    @Override
    @Transactional
    public String payOrder(String orderNo, Integer payType) {
        Order order = this.orderDao.getOrderByNo(orderNo);
        if (order == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        // 生成订单支付信息
        OrderPayInfo payInfo = new OrderPayInfo();
        payInfo.setOrderNo(orderNo);
        payInfo.setPayType(payType);
        if (order.getPayStatus() == 0) {
            payInfo.setPayStatus(1);
        } else {
            return ServiceResultEnum.DB_ERROR.getResult() + " - 该订单已支付";
        }
        payInfo.setPayTime(new Date());
        // 更新订单支付信息和状态
        this.orderDao.updateOrderPayInfo(payInfo);
        Integer orderStatus = order.getOrderStatus();
        if (orderStatus == 0) {
            // 待支付 --> 待定制
            this.orderDao.updateOrderStatus(orderNo, ++orderStatus);
        } else {
            return ServiceResultEnum.DB_ERROR.getResult() + " - 订单状态错误：该订单已支付";
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public PageResult<List<OrderVo>> getOrderList(Integer customerId, PageParam param, Integer orderStatus) {

        // 分页
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Order> orderList = this.orderDao.getOrderList(customerId, orderStatus);
        PageInfo<Order> info = new PageInfo<>(orderList);

        PageResult pageResult = new PageResult();

        pageResult.setCurrPage(info.getPageNum());
        pageResult.setPageSize(info.getPageSize());
        pageResult.setTotalCount((int)info.getTotal());
        pageResult.setTotalPage(info.getPages());


        if (orderList == null) {
            return null;
        }
        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVo orderVo = new OrderVo();
            BeanUtil.copyProperties(order, orderVo);
            // 订单状态名称
            orderVo.setOrderStatusName(this.getOrderStatusNameMap().get(order.getOrderStatus()));
            List<OrderItem> orderItems = this.orderDao.getOrderItemList(order.getOrderNo());

            orderVo.setOrderItemList(this.getOrderItemVos(orderItems));
            orderVoList.add(orderVo);
        }
        pageResult.setList(orderVoList);
        return pageResult;
    }

    @Override
    public OrderVo getOrderDetail(String orderNo) {
        Order order = this.orderDao.getOrderByNo(orderNo);
        if (order == null) {
            return null;
        }
        OrderVo orderVo = new OrderVo();
        BeanUtil.copyProperties(order, orderVo);
        // 订单状态名称
        orderVo.setOrderStatusName(this.getOrderStatusNameMap().get(order.getOrderStatus()));
        // 订单Item列表
        List<OrderItem> orderItemList = this.orderDao.getOrderItemList(orderNo);
        orderVo.setOrderItemList(this.getOrderItemVos(orderItemList));

        return orderVo;
    }

    private List<OrderItemVo> getOrderItemVos(List<OrderItem> orderItemList) {
        List<OrderItemVo> orderItemVos = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtil.copyProperties(orderItem, orderItemVo);
            // 转换定制服务信息
            orderItemVo.setService(JSON.parseObject(orderItem.getService()));
            orderItemVos.add(orderItemVo);
        }
        return orderItemVos;
    }

    private String generateOrderNo() {
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

    private Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }
}
