package com.example.heartbeatserver.service.Impl;

import com.example.heartbeatserver.controller.vo.ActivityVo;
import com.example.heartbeatserver.controller.vo.CouponVo;
import com.example.heartbeatserver.controller.vo.RankItemVo;
import com.example.heartbeatserver.controller.vo.RankVo;
import com.example.heartbeatserver.dao.ActivityDao;
import com.example.heartbeatserver.dao.CouponDao;
import com.example.heartbeatserver.entity.Activity;
import com.example.heartbeatserver.entity.Coupon;
import com.example.heartbeatserver.service.ActivityService;
import com.example.heartbeatserver.util.BeanUtil;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private PostServiceImpl postService;


    @Override
    public PageResult<List<ActivityVo>> getActivityList(PageParam param) {

        PageResult pageResult = new PageResult();
        // 分页
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Activity> activities = this.activityDao.getActivityList();
        PageInfo<Activity> info = new PageInfo<>(activities);
        pageResult.setCurrPage(info.getPageNum());
        pageResult.setPageSize(info.getPageSize());
        pageResult.setTotalPage(info.getPages());
        pageResult.setTotalCount((int)info.getTotal());

        if (activities.isEmpty()) {
            pageResult.setList(activities);
            return pageResult;
        }

        List<ActivityVo> activityVoList = new ArrayList<>();
        for(Activity activity : activities){
            ActivityVo activityVo = new ActivityVo();
            BeanUtil.copyProperties(activity, activityVo);
            if(activity.getActivityType() == 1) {
                // 如果是有奖活动，获取优惠券信息
                CouponVo couponVo = this.getCoupon(activity.getCouponId());
                activityVo.setCoupon(couponVo);
            } else {
                activityVo.setCoupon(null);
            }
            activityVoList.add(activityVo);
        }

        pageResult.setList(activityVoList);

        return pageResult;
    }

    @Override
    public ActivityVo getActivityInfo(Integer activityId) {
        Activity activity = this.activityDao.getActivityInfo(activityId);
        ActivityVo activityVo = new ActivityVo();
        BeanUtil.copyProperties(activity, activityVo);
        if (activity == null) {
            return null;
        }
        if (activity.getActivityType() == 1) {
            // 有奖活动
            CouponVo couponVo = this.getCoupon(activity.getCouponId());
            activityVo.setCoupon(couponVo);
        } else {
            activityVo.setCoupon(null);
        }
        CouponVo couponVo = this.getCoupon(activity.getCouponId());
        activityVo.setCoupon(couponVo);
        return activityVo;
    }

    @Override
    public RankVo gitActivityRank(Integer activityId, Integer customerId) {
        RankVo rankVo = new RankVo();
        // 排名项
        List<RankItemVo> itemList = this.postService.getPostRankList(activityId);
        rankVo.setActivityId(activityId);
        rankVo.setItems(itemList);
        // 用户最高排名
        Integer customerRank = this.postService.getCustomerPostRank(activityId, customerId);
        rankVo.setCustomerRank(customerRank);
        // coupon信息
        Activity activity = this.activityDao.getActivityInfo(activityId);
        rankVo.setActivityStatus(activity.getActivityStatus());
        if (activity.getActivityStatus() == 2 && customerRank == 1) {
            rankVo.setCouponId(activity.getCouponId());
        } else {
            rankVo.setCouponId(null);
        }
        return rankVo;
    }

    private CouponVo getCoupon(Integer couponId) {
        Coupon coupon = this.couponDao.getCouponInfo(couponId);
        CouponVo couponVo = new CouponVo();
        BeanUtil.copyProperties(coupon, couponVo);
        return couponVo;
    }
}
