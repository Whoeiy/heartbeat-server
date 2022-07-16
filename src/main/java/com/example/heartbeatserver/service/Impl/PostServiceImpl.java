package com.example.heartbeatserver.service.Impl;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.vo.PostVo;
import com.example.heartbeatserver.dao.PostDao;
import com.example.heartbeatserver.entity.MallCustomer;
import com.example.heartbeatserver.entity.Post;
import com.example.heartbeatserver.service.MallCustomerService;
import com.example.heartbeatserver.service.PostService;
import com.example.heartbeatserver.util.BeanUtil;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;
import com.example.heartbeatserver.util.PublicData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MallCustomerService mallCustomerService;


    @Override
    public String addPost(Post post) {
        if (this.postDao.insertPost(post) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    @Transactional
    public String updateLike(Integer postId, Integer customerId, Integer likeStatus) {
        Post post = this.postDao.getPostDetail(postId);
        if (post == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult() + " - 帖子";
        }
        String key = PublicData.POST_REDIS_KEY;
        String value = post.getActivityId() + "-" + postId;
        String customerKey = PublicData.POST_USER_REDIS_KEY + customerId + "-" + postId;
//        String customerValue = customerId + "-" + postId;

        Double score = redisTemplate.opsForZSet().score(key, value);
        if (score != null) {
            // 该活动下的该帖子有点赞记录
            if (likeStatus == 1) {
                // 用户点赞
                Object o = redisTemplate.opsForValue().get(customerKey);
                if (o != null) {
                    return "用户已经点赞过该帖子";
                } else {
                    try {
                        // 帖子点赞总数+1
                        score = redisTemplate.opsForZSet().incrementScore(key, value, 1);
                        // 记录用户点赞情况，将用户和对应帖子绑定
                        redisTemplate.opsForValue().set(customerKey, 1, 1L, TimeUnit.DAYS);
                    } catch (Exception e) {
                        return e.toString();
                    }
                }

            } else {
                // 用户取消赞
                Object o = redisTemplate.opsForValue().get(customerKey);
                if (o != null) {
                    // 用户有对该帖子的点赞记录
                    try {
                        score = redisTemplate.opsForZSet().incrementScore(key, value, -1);
                        redisTemplate.delete(customerKey);
                    } catch (Exception e) {
                        return e.toString();
                    }
                } else {
                    // 用户没有对该帖子的点赞记录
                    return "用户还未点赞过此帖子";
                }
            }
        } else {
            // 未查询到该活动下的该帖子
            if (likeStatus == 1) {
                try {
                    // 帖子总点赞数+1
                    redisTemplate.opsForZSet().add(key, value, 1);
                    score = redisTemplate.opsForZSet().score(key, value);
                    // 记录用户点赞情况
                    redisTemplate.opsForValue().set(customerKey, 1, 1L, TimeUnit.DAYS);
                } catch (Exception e) {
                    return e.toString();
                }
            } else {
                return "用户还未点赞过此帖子";
            }

        }
        return ServiceResultEnum.SUCCESS.getResult() + ","  + Math.round(score);
    }

    @Override
    public PageResult<List<PostVo>> getPostVoListOfAct(Integer activityId, Integer customerId, PageParam param) {
        String key = PublicData.POST_REDIS_KEY;

        // 分页
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Post> posts = this.postDao.getPostListOfActivity(activityId);
        PageInfo<Post> info = new PageInfo<>(posts);

        PageResult pageResult = new PageResult();
        pageResult.setCurrPage(info.getPageNum());
        pageResult.setPageSize(info.getPageSize());
        pageResult.setTotalCount((int)info.getTotal());
        pageResult.setTotalPage(info.getPages());

        List<PostVo> postVos = new ArrayList<>();

        for (Post post :
                posts) {

            String value = post.getActivityId() + "-" + post.getPostId();
            String customerKey = PublicData.POST_USER_REDIS_KEY + customerId + "-" + post.getPostId();
            PostVo postVo = new PostVo();
            BeanUtil.copyProperties(post, postVo);
            // 获取用户信息
            MallCustomer customer = mallCustomerService.getById(post.getPostUser());
            postVo.setCustomerName(customer.getCustomername());
            // 获取帖子点赞次数
            Double score = redisTemplate.opsForZSet().score(key, value);
            if (score == null) {
                score = 0.0;
            }
            postVo.setLikeCount((int)Math.round(score));
            // 获取用户点赞状态
            Object o = redisTemplate.opsForValue().get(customerKey);
            if (o != null) {
                postVo.setIsLiked(Integer.parseInt(o.toString()));
            } else {
                postVo.setIsLiked(0);
            }

            postVos.add(postVo);
        }
        pageResult.setList(postVos);
        return pageResult;
    }

    @Override
    public PostVo getPostVoDetail(Integer postId, Integer customerId) {
        String key = PublicData.POST_REDIS_KEY;

        Post post = this.postDao.getPostDetail(postId);
        if(post == null) {
            return null;
        }

        PostVo postVo = new PostVo();
        BeanUtil.copyProperties(post, postVo);

        String value = post.getActivityId() + "-" + post.getPostId();
        String customerKey = PublicData.POST_USER_REDIS_KEY + customerId + "-" + post.getPostId();

        // 获取用户信息
        MallCustomer customer = mallCustomerService.getById(post.getPostUser());
        postVo.setCustomerName(customer.getCustomername());
        // 获取帖子点赞次数
        Double score = redisTemplate.opsForZSet().score(key, value);
        if (score == null) {
            score = 0.0;
        }
        postVo.setLikeCount((int)Math.round(score));
        // 获取用户点赞状态
        Object o = redisTemplate.opsForValue().get(customerKey);
        if (o != null) {
            postVo.setIsLiked(Integer.parseInt(o.toString()));
        } else {
            postVo.setIsLiked(0);
        }

        return postVo;
    }
}
