package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.vo.NormalServiceVo;

import java.util.List;

public interface SettleService {

    List<NormalServiceVo> getNormalServiceList(Integer giftId);
}
