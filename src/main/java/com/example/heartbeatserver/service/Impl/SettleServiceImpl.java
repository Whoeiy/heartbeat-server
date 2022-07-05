package com.example.heartbeatserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.GiftServiceParam;
import com.example.heartbeatserver.controller.vo.NormalServiceVo;
import com.example.heartbeatserver.controller.vo.ServiceItemVo;
import com.example.heartbeatserver.dao.SettleDao;
import com.example.heartbeatserver.entity.Cart;
import com.example.heartbeatserver.entity.CartItem;
import com.example.heartbeatserver.entity.CustomerService;
import com.example.heartbeatserver.service.SettleService;
import com.example.heartbeatserver.service.pojo.GiftServiceInfo;
import com.example.heartbeatserver.util.PublicData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettleServiceImpl implements SettleService {

    @Autowired
    private SettleDao settleDao;

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private RedisTemplate redisTemplate;


    public Map<Integer, String> getServiceMap() {
        Map<Integer, String> serviceMap = new HashMap<>();
        serviceMap.put(1, "定制卡片");
        serviceMap.put(2, "定制包装");
        serviceMap.put(3, "定制外观");
        serviceMap.put(4, "商家特供");
        return serviceMap;
    }

    public Map<Integer, String> getChosenServiceMap() {
        Map<Integer, String> chosenServiceMap = new HashMap<>();
        chosenServiceMap.put(1, "无需定制");
        chosenServiceMap.put(2, "普通定制服务");
        chosenServiceMap.put(3, "私人定制服务");
        return chosenServiceMap;
    }


    @Override
    public List<NormalServiceVo> getNormalServiceList(Integer giftId) {
        List<CustomerService> serviceList = this.settleDao.getServiceList(giftId);
        if (serviceList == null) {
            return null;
        }

        List<NormalServiceVo> normalServiceVoList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            NormalServiceVo normalServiceVo = new NormalServiceVo();
            normalServiceVo.setServiceType(i);
            normalServiceVo.setServiceName(this.getServiceMap().get(i));
            normalServiceVo.setServiceItemVo(new ArrayList<>());
            for (CustomerService service : serviceList) {
                if (service.getServiceType() == normalServiceVo.getServiceType()) {
                    ServiceItemVo serviceItemVo = new ServiceItemVo();
                    serviceItemVo.setServiceId(service.getServiceId());
                    serviceItemVo.setServiceName(service.getServiceName());
                    serviceItemVo.setSampleImg(service.getSampleImg());
                    normalServiceVo.getServiceItemVo().add(serviceItemVo);
                }
            }
            normalServiceVoList.add(normalServiceVo);

        }
        return normalServiceVoList;
    }

    @Override
    public String updateSettleService(GiftServiceParam param, Integer customerId) {
        Cart cart = this.cartService.showCart(customerId);
        if (cart == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult() + " - 购物车不存在";
        }

        GiftServiceInfo serviceInfo = new GiftServiceInfo();
        Integer chosenType = param.getServiceChosenType();
        if (chosenType == 1) {
            // 无需定制
            serviceInfo.setServiceChosenType(chosenType);
            serviceInfo.setServiceChosenTypeName(this.getChosenServiceMap().get(chosenType));
        } else if (chosenType == 2) {
            // 普通定制
            serviceInfo.setServiceChosenType(chosenType);
            serviceInfo.setServiceChosenTypeName(this.getChosenServiceMap().get(chosenType));

            CustomerService service = this.settleDao.getService(param.getNormalServiceId());
            if (service == null) {
                return ServiceResultEnum.DATA_NOT_EXIST.getResult() + " - 服务不存在";
            }
            serviceInfo.setNormalServiceId(param.getNormalServiceId());
            serviceInfo.setNormalServiceType(service.getServiceType());
            serviceInfo.setNormalServiceTypeName(this.getServiceMap().get(service.getServiceType()));
            serviceInfo.setNormalServiceName(service.getServiceName());
            serviceInfo.setServiceNote(param.getServiceNote());
        } else if (chosenType == 3) {
            // 私人定制
            serviceInfo.setServiceChosenType(chosenType);
            serviceInfo.setServiceChosenTypeName(this.getChosenServiceMap().get(chosenType));
            serviceInfo.setServiceNote(param.getServiceNote());
            serviceInfo.setPhone(param.getPhone());
        } else {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult() + " - 该类普通服务不存在";
        }

        List<CartItem> cartItemList = cart.getCartItemList();
        for (int i = 0; i < cartItemList.size(); i++) {
            if (cartItemList.get(i).getGiftId() == param.getGiftId()) {
                // 找到购物车中该礼物，更新定制服务信息
                cartItemList.get(i).setService(JSON.toJSONString(serviceInfo));
                break;
            }
        }
        // 更新redis数据
        redisTemplate.opsForHash().put(PublicData.CART_REDIS_KEY,
                PublicData.CART_REDIS_ITEM_NAME + customerId, JSON.toJSONString(cart));
        return ServiceResultEnum.SUCCESS.getResult();

    }

}
