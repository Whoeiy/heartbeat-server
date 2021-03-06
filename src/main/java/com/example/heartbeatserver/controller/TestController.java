package com.example.heartbeatserver.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/a")
@RestController
public class TestController {


    @GetMapping("/hello")
    @ApiOperation("测试swagger配置")
    public String hello(){
        //request
        return "hello";
    }



}
