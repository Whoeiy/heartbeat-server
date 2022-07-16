package com.example.heartbeatserver.dao;

import com.example.heartbeatserver.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostDao {

    // 新增帖子
    Integer insertPost(Post post);

    // 查询帖子详情
    Post getPostDetail(Integer postId);

    // 查询某活动帖子列表
    List<Post> getPostListOfActivity(Integer activityId);
}
