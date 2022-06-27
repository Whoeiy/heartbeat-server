package com.example.heartbeatserver.controller.vo;

import com.example.heartbeatserver.entity.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class CartVo {

    private Integer customerId;

    private List<CartItemVo> cartItems;

    private Double totalPrice;
}
