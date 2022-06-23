package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.EsCategory;
import com.example.heartbeatserver.entity.EsGift;
import com.example.heartbeatserver.entity.EsLabel;
import com.example.heartbeatserver.entity.Gift;
import com.example.heartbeatserver.service.pojo.GiftCategoryIds;
import com.example.heartbeatserver.service.pojo.GiftLabelsIds;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EsGiftDao {

    // 查询礼物
    List<Gift> getAllGiftList();

    // 查询礼物所属分类信息
    List<EsCategory> getGiftCategoriesByIds(@Param("ids") List<Integer> ids);

    // 查询礼物分类列表
    GiftCategoryIds getCategoriesIds(Integer third);

    // 查询礼物标签信息
    List<EsLabel> getGiftLabelsByIds(@Param("ids") List<Integer> ids);

    // 查询礼物标签列表
    GiftLabelsIds getLabelsIds(Integer third);

    // 根据id查询礼物信息
    Gift getGiftById(Integer giftId);


}
