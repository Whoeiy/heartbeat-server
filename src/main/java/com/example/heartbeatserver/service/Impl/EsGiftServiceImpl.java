package com.example.heartbeatserver.service.Impl;

//import com.example.heartbeatserver.controller.EsGiftRepository;
import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.EsGiftRepository;
import com.example.heartbeatserver.dao.EsGiftDao;
import com.example.heartbeatserver.entity.*;
import com.example.heartbeatserver.service.EsGiftService;
import com.example.heartbeatserver.service.pojo.GiftCategoryIds;
import com.example.heartbeatserver.service.pojo.GiftLabelsIds;
import com.example.heartbeatserver.util.BeanUtil;
import com.example.heartbeatserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EsGiftServiceImpl implements EsGiftService {

    @Autowired
    private EsGiftDao esGiftDao;

    @Autowired
    private EsGiftRepository esGiftRepository;

    @Override
    public int importAll() {
        int res = 0;
        List<Gift> giftOrgList = this.esGiftDao.getAllGiftList();
        List<EsCategory> esCategories = new ArrayList<>();
        List<List<EsLabel>> esLabels = new ArrayList<>();
        List<EsGift> esGifts = new ArrayList<>();
        for (Gift gift : giftOrgList) {
            EsGift esGift = new EsGift();
            BeanUtil.copyProperties(gift, esGift);
            esCategories = this.getGiftCategories(gift);
            esLabels = this.getGiftLabels(gift);
            esGift.setGiftCategory(esCategories);
            esGift.setGiftLabelList(esLabels);
            esGifts.add(esGift);
        }
        Iterable<EsGift> esGiftIterable = this.esGiftRepository.saveAll(esGifts);
        Iterator<EsGift> iterator = esGiftIterable.iterator();
        while(iterator.hasNext()) {
            res++;
            iterator.next();
        }
        return res;
    }

    public List<EsCategory> getGiftCategories(Gift gift) {
        GiftCategoryIds temp = this.getCategoryIds(gift.getGiftThirdCategoryId());
        List<Integer> ids = new ArrayList<>();
        ids.add(temp.getFirst());
        ids.add(temp.getSecond());
        ids.add(temp.getThird());
//        System.out.println(ids);
        List<EsCategory> categories = this.esGiftDao.getGiftCategoriesByIds(ids);
        return categories;
    }

    public GiftCategoryIds getCategoryIds(Integer third){
        GiftCategoryIds temp = this.esGiftDao.getCategoriesIds(third);
        temp.setThird(third);
        return temp;
    }

    public List<List<EsLabel>> getGiftLabels(Gift gift) {
        String labelIds = gift.getGiftLabelIdList();
        if (labelIds == null || labelIds.length()<=0) {
            return null;
        }
        String[] temp = labelIds.split(",");
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            ids.add(Integer.parseInt(temp[i]));
        }
        List<List<EsLabel>> labels = new ArrayList<>();
        for(int id : ids) {
            List<Integer> levelIds = this.getLabelLevelIds(id);
            if (levelIds == null) {
                return labels;
            }
            List<EsLabel> labelNewList = this.esGiftDao.getGiftLabelsByIds(levelIds);
            labels.add(labelNewList);
        }

        return labels;
    }

    public List<Integer> getLabelLevelIds(Integer third){
        GiftLabelsIds temp = this.esGiftDao.getLabelsIds(third);
        if (temp == null) {
            return null;
        }
        temp.setThird(third);
        List<Integer> ids = new ArrayList<>();
        ids.add(temp.getFirst());
        ids.add(temp.getSecond());
        ids.add(temp.getThird());
        return ids;
    }

    @Override
    public void delete(Integer giftId) {
        esGiftRepository.deleteById(giftId);
    }

    @Override
    public EsGift create(Integer giftId) {
        EsGift esGift = new EsGift();
        Gift gift = esGiftDao.getGiftById(giftId);
        if (gift != null) {
            BeanUtil.copyProperties(gift, esGift);
            List<EsCategory> esCategories = this.getGiftCategories(gift);
            List<List<EsLabel>> esLabels = this.getGiftLabels(gift);
            esGift.setGiftCategory(esCategories);
            esGift.setGiftLabelList(esLabels);
        }
        return esGift;
    }

    @Override
    public List<EsGift> simpleSearch(String keyword, PageParam pageParam) {
//        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        return esGiftRepository.findByGiftNameOrGiftIntro(keyword, keyword);
    }

    @Override
    public List<EsGift> sortSearch(String keyword, PageParam pageParam) {
        return null;
    }

    @Override
    public EsGift getGiftDetailById(Integer giftId) {
        Gift gift = this.esGiftDao.getGiftById(giftId);
        List<EsCategory> esCategories = new ArrayList<>();
        List<List<EsLabel>> esLabels = new ArrayList<>();
        EsGift esGift = new EsGift();
        BeanUtil.copyProperties(gift, esGift);
        esCategories = this.getGiftCategories(gift);
        esLabels = this.getGiftLabels(gift);
        esGift.setGiftCategory(esCategories);
        esGift.setGiftLabelList(esLabels);
        return esGift;
    }

    @Override
    public String recommendGift(Integer giftId) {
        if (this.esGiftDao.recommendGift(giftId) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
}
