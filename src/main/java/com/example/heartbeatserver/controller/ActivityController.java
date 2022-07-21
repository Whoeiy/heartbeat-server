package com.example.heartbeatserver.controller;


import com.example.heartbeatserver.common.ServiceResultEnum;
import com.example.heartbeatserver.controller.vo.ActivityVo;
import com.example.heartbeatserver.controller.vo.RankItemVo;
import com.example.heartbeatserver.controller.vo.RankVo;
import com.example.heartbeatserver.service.Impl.ActivityServiceImpl;
import com.example.heartbeatserver.service.Impl.PostServiceImpl;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.PageResult;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/a/activity")
public class ActivityController {

    @Autowired
    private ActivityServiceImpl activityService;

    @Autowired
    private PostServiceImpl postService;


    @GetMapping("/list")
    @ApiOperation("/查询活动列表")
    public Result getActivityList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageParam param = new PageParam(pageNum, pageSize);
        PageResult pageResult = this.activityService.getActivityList(param);
        return ResultGenerator.genSuccessResultData(pageResult);
    }

    @GetMapping("/{activityId}")
    @ApiOperation("/查询活动详情")
    public Result<ActivityVo> getActivityInf(@PathVariable Integer activityId) {
        ActivityVo activityVo = this.activityService.getActivityInfo(activityId);
        if (activityVo == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResultData(activityVo);
    }

    @GetMapping("/{activityId}/chart")
    @ApiOperation("/查询活动排行榜")
    public Result<RankVo> getActivityRank(@PathVariable Integer activityId, Integer customerId){
        RankVo rankVo = this.activityService.gitActivityRank(activityId, customerId);
        return ResultGenerator.genSuccessResultData(rankVo);
    }

}
