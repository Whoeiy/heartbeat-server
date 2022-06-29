package com.example.heartbeatserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.heartbeatserver.common.CartEnum;
import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.AddGiftIntoCartParam;
import com.example.heartbeatserver.controller.vo.CartItemVo;
import com.example.heartbeatserver.controller.vo.CartVo;
import com.example.heartbeatserver.dao.EsGiftDao;
import com.example.heartbeatserver.entity.Cart;
import com.example.heartbeatserver.entity.CartGiftDes;
import com.example.heartbeatserver.entity.CartItem;
import com.example.heartbeatserver.entity.Gift;
import com.example.heartbeatserver.service.CartService;
import com.example.heartbeatserver.util.PublicData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EsGiftDao esGiftDao;

    @Override
    public String addGiftIntoCart(Integer customerId, AddGiftIntoCartParam param) {
        // 获取该用户的购物车对象
        Cart cart = showCart(customerId);

        if (cart == null) {
            cart = new Cart();
            CartItem cartItem = new CartItem(param.getGiftId(), param.getPrice(), param.getCount());
            List<CartItem> cartItemList = new ArrayList<>();
            cart.setCustomerId(customerId);
            cartItemList.add(cartItem);
            cart.setCartItemList(cartItemList);
        } else {
            List<CartItem> cartItemList = cart.getCartItemList();
            boolean isExisted = false;
            for (int i = 0; i <cartItemList.size(); i++) {
              if (cartItemList.get(i).getGiftId() == param.getGiftId()) {
                 cartItemList.get(i).setCount(cartItemList.get(i).getCount() + 1);
                 isExisted = true;
                 break;
              }
            }
            if (!isExisted) {
                CartItem cartItem = new CartItem(param.getGiftId(), param.getPrice(), param.getCount());
                cart.getCartItemList().add(cartItem);
            }
        }
        // 更新redis数据
        redisTemplate.opsForHash().put(PublicData.CART_REDIS_KEY,
                PublicData.CART_REDIS_ITEM_NAME + customerId, JSON.toJSONString(cart));
        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public Cart showCart(Integer customerId) {
        String itemName = PublicData.CART_REDIS_ITEM_NAME + customerId;
        Object obj = redisTemplate.opsForHash().get(PublicData.CART_REDIS_KEY, itemName);
        if (obj != null) {
            Cart cart = JSON.parseObject(obj.toString(), Cart.class);
            return cart;
        }
        return null;
    }

    @Override
    public CartVo showCartVo(Integer customerId) {
        Cart cart = showCart(customerId);

        if (cart == null) {
            return null;
        }

        CartVo cartVo = new CartVo();
        cartVo.setCustomerId(customerId);

        List<CartItem> cartItemList = cart.getCartItemList();
        List<CartItemVo> cartItemVoList = new ArrayList<>();
        Double sum = 0.00;
        for (CartItem cartItem : cartItemList) {
            String itemName = PublicData.GIFT_DES_REDIS_ITEM_NAME + cartItem.getGiftId();
            Object obj = redisTemplate.opsForHash().get(PublicData.GIFT_DES_REDIS_KEY, itemName);
            if (obj != null) {
                CartGiftDes cartGiftDes = JSON.parseObject(obj.toString(), CartGiftDes.class);
                CartItemVo cartItemVo = new CartItemVo();
                cartItemVo.setGiftId(cartItem.getGiftId());
                cartItemVo.setCount(cartItem.getCount());
                cartItemVo.setPrice(cartItem.getPrice());
                cartItemVo.setGiftImg(cartGiftDes.getImgUrl());
                cartItemVo.setGiftName(cartGiftDes.getGiftName());
                cartItemVoList.add(cartItemVo);

                Double itemPrice = cartItem.getPrice() * cartItem.getCount();
                sum += itemPrice;
            }
        }
        cartVo.setCartItems(cartItemVoList);
        cartVo.setTotalPrice(sum);

        return cartVo;
    }

    @Override
    public String updateGiftDesRedis() {
        redisTemplate.delete(PublicData.GIFT_DES_REDIS_KEY);
        List<CartGiftDes> cartGiftDesList = this.esGiftDao.getGiftDes();
        for (CartGiftDes cartGiftDes : cartGiftDesList) {
            redisTemplate.opsForHash().put(PublicData.GIFT_DES_REDIS_KEY,
                    PublicData.GIFT_DES_REDIS_ITEM_NAME + cartGiftDes.getGiftId(), JSON.toJSONString(cartGiftDes));
        }
        return CartEnum.UPDATE_GIFT_DES_IN_REDIS_SUCCESS.getMessage();
    }

    @Override
    public String deleteGiftInCart(Integer customerId, Integer giftId) {
        // 查询该用户是否有购物车
        Cart cart = showCart(customerId);
        if (cart == null) {
            return CartEnum
                    .CART_NOT_EXISTS.getMessage();
        }
        // 遍历cartItem查找目标gift
        List<CartItem> cartItemList = cart.getCartItemList();
        for (int i = 0; i < cartItemList.size(); i++) {
            CartItem cartItem = cartItemList.get(i);
            if (cartItem.getGiftId() == giftId) {
                cartItem.setCount(cartItem.getCount() - 1);
                if (cartItem.getCount() == 0) {
                    cartItemList.remove(cartItem);
                }
                break;
            }
        }

        String itemName = PublicData.CART_REDIS_ITEM_NAME + customerId;
        // 判断购物车里是否还有礼物。如果没有，则删除购物车
        if (cart.getCartItemList().size() == 0) {
            redisTemplate.opsForHash().delete(PublicData.CART_REDIS_KEY, itemName);
        } else {
            redisTemplate.opsForHash().put(PublicData.CART_REDIS_KEY, itemName, JSON.toJSONString(cart));
        }

        return CartEnum.CART_DEL_GIFT_SUCCESS.getMessage();
    }

    @Override
    public String buyCart(Integer customerId, Integer[] giftIds, Integer addressId) {
        // 查询该用户是否存在购物车
        Cart oldCart = showCart(customerId);
        if (oldCart == null) {
            return CartEnum
                    .CART_NOT_EXISTS.getMessage();
        }
        // 声明要购买的购物车信息
        Cart buyCart = new Cart();
        buyCart.setCustomerId(customerId);

        // 声明要保存到redis里的购物车信息
        Cart saveCart = new Cart();
        saveCart.setCustomerId(customerId);

        // 遍历礼物列表
        List<CartItem> cartItemList = oldCart.getCartItemList();
        for (Integer giftId : giftIds) {
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getGiftId() == giftId) {
                    buyCart.getCartItemList().add(cartItem);
                    break;
                }
            }
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }
}
