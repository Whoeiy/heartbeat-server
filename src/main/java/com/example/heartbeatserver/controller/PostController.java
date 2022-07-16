package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.param.AddPostParam;
import com.example.heartbeatserver.controller.param.LikePostParam;
import com.example.heartbeatserver.controller.vo.PostVo;
import com.example.heartbeatserver.entity.Post;
import com.example.heartbeatserver.service.Impl.PostServiceImpl;
import com.example.heartbeatserver.util.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/a/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @PostMapping
    @ApiOperation("/新增帖子")
    public Result<String> addPost(@RequestBody AddPostParam param, Integer customerId) {
        Post post = new Post();
        BeanUtil.copyProperties(param, post);
        post.setPostUser(customerId);
        post.setCreateTime(new Date());

        String res = this.postService.addPost(post);
        if (res.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult(res);
    }

    @PostMapping("/like")
    @ApiOperation("/点赞或取消赞")
    public Result<String> likeOrCancel(@RequestBody LikePostParam param, Integer customerId) {
        String res = this.postService.updateLike(param.getPostId(), customerId, param.getLikeStatus());
        String[] arr = res.split(",");
        if (arr[0].equals(ServiceResultEnum.SUCCESS.getResult())) {
            return ResultGenerator.genSuccessResultData(Integer.parseInt(arr[1]));
        }
        return ResultGenerator.genFailResult(res);
    }

    @GetMapping("/list")
    @ApiOperation("/查询活动帖子列表")
    public Result<PageResult> getPostList(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                          @RequestParam Integer activityId, Integer customerId) {

        PageParam param = new PageParam(pageNum, pageSize);
        PageResult pageResult = this.postService.getPostVoListOfAct(activityId, customerId, param);
        return ResultGenerator.genSuccessResultData(pageResult);
    }

    @GetMapping("/{postId}")
    @ApiOperation("/查询帖子详情")
    public Result<PostVo> getPostDetail(@PathVariable Integer postId, Integer customerId) {
        PostVo postVo = this.postService.getPostVoDetail(postId, customerId);
        if (postVo != null) {
            return ResultGenerator.genSuccessResultData(postVo);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }
}
