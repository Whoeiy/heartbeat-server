package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.service.Impl.IndexInfoServiceImpl;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/a/index")
public class IndexInfoController {

    @Autowired
    private IndexInfoServiceImpl indexInfoService;

    @GetMapping("/info")
    @ApiOperation("/查询首页配置信息")
    public Result indexInfo() {
        return ResultGenerator.genSuccessResultData(this.indexInfoService.getIndexInfo());
    }
}
