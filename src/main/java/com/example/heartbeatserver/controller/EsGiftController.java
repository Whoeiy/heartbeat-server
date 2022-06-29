package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.entity.EsGift;
import com.example.heartbeatserver.service.Impl.EsGiftServiceImpl;
import com.example.heartbeatserver.util.PageParam;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/a")
public class EsGiftController {

    @Autowired
    private EsGiftServiceImpl esGiftService;

    @PostMapping("/es/importAll")
    public Result importAllList(){
        int count = esGiftService.importAll();
        return ResultGenerator.genSuccessResultData(count);
    }

    @GetMapping("/es/search")
    public Result<List<EsGift>> search(@RequestParam String keyword) {
        PageParam pageParam = new PageParam(1,10);
        List<EsGift> esGiftList = esGiftService.simpleSearch(keyword, pageParam);
        return ResultGenerator.genSuccessResultData(esGiftList);
    }

    @GetMapping("/gift/detail/{giftId}")
    public Result<EsGift> getGiftDetail(@PathVariable Integer giftId) {
        EsGift esGift = this.esGiftService.getGiftDetailById(giftId);
        if (esGift != null) {
            return ResultGenerator.genSuccessResultData(esGift);
        }
        return ResultGenerator.genFailResult("未查询到该礼物信息");
    }

}
