package com.example.heartbeatserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.heartbeatserver.common.CartEnum;
import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.AddGiftIntoCartParam;
import com.example.heartbeatserver.controller.vo.CartItemVo;
import com.example.heartbeatserver.controller.vo.CartVo;
import com.example.heartbeatserver.dao.AddressDao;
import com.example.heartbeatserver.dao.EsGiftDao;
import com.example.heartbeatserver.entity.Cart;
import com.example.heartbeatserver.entity.CartGiftDes;
import com.example.heartbeatserver.entity.CartItem;
import com.example.heartbeatserver.entity.Gift;
import com.example.heartbeatserver.service.CartService;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;
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

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public String addGiftIntoCart(Integer customerId, AddGiftIntoCartParam param) {
        // 获取该用户的购物车对象
        Cart cart = showCart(customerId);

        if (cart == null) {
            // 该用户没有购物车
            cart = new Cart();
            CartItem cartItem = new CartItem(param.getGiftId(), param.getPrice(), null, param.getCount());
            List<CartItem> cartItemList = new ArrayList<>();
            cart.setCustomerId(customerId);
            cartItemList.add(cartItem);
            cart.setCartItemList(cartItemList);
        } else {
            // 该用户有购物车
            List<CartItem> cartItemList = cart.getCartItemList();
            boolean isExisted = false;
            for (int i = 0; i < cartItemList.size(); i++) {
              if (cartItemList.get(i).getGiftId() == param.getGiftId()) {
                  // 若该礼物已加入购物车，增加该礼物的数量
                 cartItemList.get(i).setCount(cartItemList.get(i).getCount() + 1);
                 isExisted = true;
                 break;
              }
            }
            if (!isExisted) {
                // 若该礼物未加入购物车
                CartItem cartItem = new CartItem(param.getGiftId(), param.getPrice(), null, param.getCount());
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
            this.removeCart(customerId);
        } else {
            this.updateCart(customerId, cart);
        }

        return CartEnum.CART_DEL_GIFT_SUCCESS.getMessage();
    }

    @Override
    public CartVo settle(Integer customerId, Integer[] giftIds) {
        // 查询该用户是否存在购物车
        Cart oldCart = showCart(customerId);
        if (oldCart == null) {
            return null;
        }
        // 声明要购买的购物车信息
        Cart buyCart = new Cart();
        buyCart.setCustomerId(customerId);
        buyCart.setCartItemList(new ArrayList<>());

        CartVo buyCartVo = new CartVo();
        buyCartVo.setCustomerId(customerId);

        // 遍历礼物列表, 生成要购买礼物列表
        List<CartItem> cartItemList = oldCart.getCartItemList();

        for(CartItem cartItem : cartItemList) {
            for (int i = 0; i < giftIds.length; i++) {
                if (cartItem.getGiftId() == giftIds[i]) {
                    buyCart.getCartItemList().add(cartItem);
                    break;
                }
            }
        }

        List<CartItemVo> cartItemVoList = new ArrayList<>();

        Double sum = 0.00;
        for (CartItem cartItem : buyCart.getCartItemList()) {
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
                cartItemVo.setService(JSON.parseObject(cartItem.getService()));
                // 计算小计金额
                Double itemPrice = cartItem.getPrice() * cartItem.getCount();
                cartItemVo.setSellingPrice(itemPrice);

                cartItemVoList.add(cartItemVo);
                sum += itemPrice;
            }
        }
        buyCartVo.setCartItems(cartItemVoList);
        buyCartVo.setTotalPrice(sum);

        return buyCartVo;
    }


    @Override
    public String saveOrder(Integer customerId, Integer[] giftIds, Integer addressId) {
        // 查询该用户是否存在购物车
        Cart oldCart = showCart(customerId);
        if (oldCart == null) {
            return CartEnum.CART_NOT_EXISTS.getMessage();
        }
        // 声明要购买的购物车信息
        Cart buyCart = new Cart();
        buyCart.setCustomerId(customerId);
        buyCart.setCartItemList(new ArrayList<>());

        // 声明要保存到redis里的购物车信息
        Cart saveCart = new Cart();
        saveCart.setCustomerId(customerId);
        saveCart.setCartItemList(new ArrayList<>());

        // 遍历礼物列表
        List<CartItem> cartItemList = oldCart.getCartItemList();

        for(CartItem cartItem : cartItemList) {
            int i = 0;
            for (; i < giftIds.length; i++) {
                if (cartItem.getGiftId() == giftIds[i]) {
                    buyCart.getCartItemList().add(cartItem);
                    break;
                }
            }
            // 如果正常退出循环，说明该cartItem需要存入redis
            if (i >= giftIds.length) {
                saveCart.getCartItemList().add(cartItem);
            }
        }

        // 要购买的礼物不在购物车中
        if (buyCart.getCartItemList().size() == 0) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult() + " - 该礼物不在购物车中";
        }

        // 完成购买操作
        String res = this.orderService.createOrder(buyCart, addressId);
        String[] resArr = res.split(",");
        if (resArr[0].equals(ServiceResultEnum.SUCCESS.getResult())){
            // 创建订单成功
            // 更新redis数据
            // 当购物车里剩余商品信息为空时，表示购物车已被清空，需要清除当前用户的购物车
            if(saveCart.getCartItemList().size() == 0) {
                this.removeCart(customerId);
            } else {
                this.updateCart(customerId, saveCart);
            }
        }
        return res;
    }

    private void removeCart(Integer customerId) {
        String itemName = PublicData.CART_REDIS_ITEM_NAME + customerId;
        redisTemplate.opsForHash().delete(PublicData.CART_REDIS_KEY, itemName);
        return;
    }

    private void updateCart(Integer customerId, Cart cart) {
        String itemName = PublicData.CART_REDIS_ITEM_NAME + customerId;
        redisTemplate.opsForHash().put(PublicData.CART_REDIS_KEY, itemName, JSON.toJSONString(cart));
        return;
    }
}
