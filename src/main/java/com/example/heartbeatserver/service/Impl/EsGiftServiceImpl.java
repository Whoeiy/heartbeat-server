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
import com.example.heartbeatserver.util.PageResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EsGiftServiceImpl implements EsGiftService {

    @Autowired
    private EsGiftDao esGiftDao;

    @Autowired
    private EsGiftRepository esGiftRepository;

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

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

    public List<EsGift> testSearch(Integer isDeleted) {
        return esGiftRepository.findAllByIsDeletedEquals(isDeleted);
    }

    private Map<String, Float> queryFields() {
        Map<String, Float> fields = new HashMap<>();
        fields.put("giftName", 3F);
        fields.put("giftIntro", 2F);
//        fields.put("giftCategory.categoryName", 5F);
//        fields.put("giftLabelList.labelName", 4F);
        return fields;
    }

    @Override
    public PageResult<List<EsGift>> Search(String keyword, Integer searchType, Integer sort, PageParam param) {
        PageResult pageResult = new PageResult();
        pageResult.setPageSize(param.getPageSize());
        pageResult.setCurrPage(param.getPageNum());

        NativeSearchQuery nativeSearchQuery = null;
        if (searchType == 1) {
            // 分类搜索
            nativeSearchQuery = this.categoryOrLabelQuery(keyword, 1, sort, param);
        } else if (searchType == 2) {
            // 标签搜索
            nativeSearchQuery = this.categoryOrLabelQuery(keyword, 2, sort, param);
        } else {
            // 综合搜索
            nativeSearchQuery = this.generalQuery(keyword, sort, param);
        }
        SearchHits<EsGift> searchHits = restTemplate.search(nativeSearchQuery, EsGift.class);
        pageResult.setTotalCount((int)searchHits.getTotalHits());
        Integer page = 0;
        if (pageResult.getTotalCount() != 0) {
            page = pageResult.getTotalCount() / pageResult.getPageSize() + 1;
        }
        pageResult.setTotalPage(page);

        List<EsGift> esGiftList = new ArrayList<>();
        for(SearchHit<EsGift> searchHit : searchHits) {
            EsGift esGift = searchHit.getContent();
            esGiftList.add(esGift);
        }

        pageResult.setList(esGiftList);
        return pageResult;
    }

    private NativeSearchQuery categoryOrLabelQuery(String keyword, Integer search, Integer sort, PageParam param) {
        String searchType = "giftCategory.categoryName";
        if (search == 2) {
            // 按照标签匹配
            searchType = "giftLabelList.labelName";
        }
        FieldSortBuilder sortType = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
        if (sort == 2) {
            sortType = SortBuilders.fieldSort("originalPrice").order(SortOrder.DESC);
        }
        if (sort == 3) {
            sortType = SortBuilders.fieldSort("originalPrice").order(SortOrder.ASC);
        }
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchPhraseQuery(searchType, keyword)))
                .withSorts(sortType)
                .withPageable(PageRequest.of(param.getPageNum() - 1, param.getPageSize()))
                .build();
        return nativeSearchQuery;
    }

    private NativeSearchQuery generalQuery(String keyword, Integer sort, PageParam param) {
        FieldSortBuilder sortType = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
        if (sort == 2) {
            sortType = SortBuilders.fieldSort("originalPrice").order(SortOrder.DESC);
        }
        if (sort == 3) {
            sortType = SortBuilders.fieldSort("originalPrice").order(SortOrder.ASC);
        }
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.queryStringQuery(keyword).field("giftName"))
                        .should(QueryBuilders.queryStringQuery(keyword).field("giftCategory.categoryName"))
                        .should(QueryBuilders.queryStringQuery(keyword).field("giftLabelList.labelName")))
                .withSorts(sortType)
                .withPageable(PageRequest.of(param.getPageNum() - 1, param.getPageSize()))
                .build();
        return nativeSearchQuery;
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
