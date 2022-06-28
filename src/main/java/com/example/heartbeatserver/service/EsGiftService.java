package com.example.heartbeatserver.service;

import com.example.heartbeatserver.entity.EsGift;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;

import java.util.List;

public interface EsGiftService {

    int importAll();

    void delete(Integer giftId);

    // 根据id创建商品
    EsGift create(Integer giftId);

    // 简单搜索
    List<EsGift> simpleSearch(String keyword, PageParam pageParam);

    // 综合搜索
    List<EsGift> sortSearch(String keyword, PageParam pageParam);

    // 根据giftId查询礼物详情
    EsGift getGiftDetailById(Integer giftId);


}
