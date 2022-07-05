package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.param.GiftServiceParam;
import com.example.heartbeatserver.controller.vo.NormalServiceVo;

import java.util.List;

public interface SettleService {

    // 查询定制服务列表
    List<NormalServiceVo> getNormalServiceList(Integer giftId);

    // 更新购物车中定制服务信息
    String updateSettleService(GiftServiceParam param, Integer customerId);
}
