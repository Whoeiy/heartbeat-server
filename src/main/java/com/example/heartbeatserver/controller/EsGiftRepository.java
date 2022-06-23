package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.entity.EsGift;
import com.example.heartbeatserver.util.PageResult;
import com.example.heartbeatserver.util.Result;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsGiftRepository extends ElasticsearchRepository<EsGift, Integer> {
    List<EsGift> findByGiftNameOrGiftIntro(String giftName, String giftIntro);
}
