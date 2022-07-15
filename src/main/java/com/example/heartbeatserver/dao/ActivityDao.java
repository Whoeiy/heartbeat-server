package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityDao {

    // 查询活动列表
    List<Activity> getActivityList();

    // 查询活动详情
    Activity getActivityInfo(Integer activityId);
}
