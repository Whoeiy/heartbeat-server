package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.vo.ActivityVo;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;

import java.util.List;

public interface ActivityService {

    // 查询活动列表
    PageResult<List<ActivityVo>> getActivityList(PageParam param);

    // 查询活动详情
    ActivityVo getActivityInfo(Integer activityId);
}
