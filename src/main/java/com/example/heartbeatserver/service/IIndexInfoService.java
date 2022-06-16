package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.vo.CarouselVo;
import com.example.heartbeatserver.controller.vo.IndexConfigGiftVo;
import com.example.heartbeatserver.controller.vo.IndexInfoVo;
import com.example.heartbeatserver.controller.vo.IndexLabelVo;

import java.util.List;

public interface IIndexInfoService {
    List<CarouselVo> getCarousels();

    List<IndexLabelVo> getIndexLabels();

    List<IndexConfigGiftVo> getIndexConfigGifts(Integer type);

    IndexInfoVo getIndexInfo();
}
