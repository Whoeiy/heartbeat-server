package com.example.heartbeatserver.service;

import com.example.heartbeatserver.controller.vo.PostVo;
import com.example.heartbeatserver.entity.Post;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;

import java.util.List;

public interface PostService {

    // 新增帖子
    String addPost(Post post);

    // 点赞或取消赞
    String updateLike(Integer postId, Integer customerId, Integer likeStatus);

    // 查询帖子列表
    PageResult<List<PostVo>> getPostVoListOfAct(Integer activityId, Integer customerId, PageParam param);

    // 查询帖子详情
    PostVo getPostVoDetail(Integer postId, Integer customerId);
}
