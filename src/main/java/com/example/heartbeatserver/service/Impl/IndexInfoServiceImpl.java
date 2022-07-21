package com.example.heartbeatserver.service.Impl;

import com.example.heartbeatserver.controller.vo.CarouselVo;
import com.example.heartbeatserver.controller.vo.IndexConfigGiftVo;
import com.example.heartbeatserver.controller.vo.IndexInfoVo;
import com.example.heartbeatserver.controller.vo.IndexLabelVo;
import com.example.heartbeatserver.dao.IndexInfoDao;
import com.example.heartbeatserver.service.IIndexInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexInfoServiceImpl implements IIndexInfoService {

    @Autowired
    private IndexInfoDao indexInfoDao;

    @Override
    public List<CarouselVo> getCarousels() {
        return this.indexInfoDao.getCarouselList();
    }

    @Override
    public List<IndexLabelVo> getIndexLabels() {
        return this.indexInfoDao.getIndexLabelList();
    }

    @Override
    public List<IndexConfigGiftVo> getIndexConfigGifts(Integer type) {
        return this.indexInfoDao.getIndexConfigGiftList(type);
    }

    @Override
    public IndexInfoVo getIndexInfo() {
        IndexInfoVo indexInfoVo = new IndexInfoVo();
        indexInfoVo.setCarousels(this.indexInfoDao.getCarouselList());
        indexInfoVo.setLabels(this.indexInfoDao.getIndexLabelList());
        indexInfoVo.setHotGifts(this.indexInfoDao.getIndexConfigGiftList(1));
        indexInfoVo.setNewGifts(this.indexInfoDao.getIndexConfigGiftList(2));
//        indexInfoVo.setRecommendGifts(this.indexInfoDao.getIndexConfigGiftList(3));
        indexInfoVo.setRecommendGifts(this.getRecommendedGift());
        return indexInfoVo;
    }

    private List<IndexConfigGiftVo> getRecommendedGift() {
        List<IndexConfigGiftVo> indexConfigGiftVoList = this.indexInfoDao.getRecommendedGiftList();
        return indexConfigGiftVoList;
    }

}
