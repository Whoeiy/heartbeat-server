package com.example.heartbeatserver.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Cart implements Serializable {
    private Integer customerId;

    private List<CartItem> cartItemList;

    private Double totalPrice;
}
