package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.controller.vo.CarouselVo;
import com.example.heartbeatserver.controller.vo.IndexConfigGiftVo;
import com.example.heartbeatserver.controller.vo.IndexLabelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndexInfoDao {
    // 查询轮播图列表
    List<CarouselVo> getCarouselList();

    // 查询标签列表
    List<IndexLabelVo> getIndexLabelList();

    // 查询首页配置礼物列表
    List<IndexConfigGiftVo> getIndexConfigGiftList(Integer configType);

    // 查询推荐礼物列表
    List<IndexConfigGiftVo> getRecommendedGiftList();
}
