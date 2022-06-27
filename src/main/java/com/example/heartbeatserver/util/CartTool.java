package com.example.heartbeatserver.util;

import com.example.heartbeatserver.entity.Cart;
import com.example.heartbeatserver.entity.CartItem;

public class CartTool {

    public static Cart createCart(String[] cartList) {
        Cart cart = new Cart();
        Double sum = 0.0;
        for (String pro : cartList) {
            String[] strs = pro.split(",");
            int giftId = Integer.parseInt(strs[0]);
            int count = Integer.parseInt(strs[1]);
            Double price = Double.parseDouble(strs[2]);
            int customerId = Integer.parseInt(strs[3]);

            CartItem cartItem = new CartItem();
            cartItem.setGiftId(giftId);
            cartItem.setPrice(price);
            cartItem.setCount(count);

            sum += price;
            cart.setCustomerId(customerId);
            cart.getCartItemList().add(cartItem);
        }
        cart.setTotalPrice(sum);
        return cart;
    }
}
