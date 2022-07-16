package com.example.heartbeatserver.controller;

import com.example.heartbeatserver.util.HeartBeatUtils;
import com.example.heartbeatserver.util.Result;
import com.example.heartbeatserver.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/a/upload")
public class FileUploadController {

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd");
    @Value("${file.server_path}")   // 服务器地址
//    @Value("${file.local_path}")
    private String path;

    @PostMapping("/file")
    @ApiOperation("单个文件上传")
    public Result uploadFile(HttpServletRequest req, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultGenerator.genFailResult("文件不能为空");
        }
        String originalName = file.getOriginalFilename();
        if (!(originalName.endsWith(".png") || originalName.endsWith(".jpeg") || originalName.endsWith(".jpg"))) {
            return ResultGenerator.genFailResult("上传文件失败,文件格式不支持");
        }
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String fileName = UUID.randomUUID().toString() + originalName.substring(originalName.lastIndexOf("."));
        File dest = new File(path, fileName);
        String temp = "";
        try {
            file.transferTo(dest);
            temp += HeartBeatUtils.getHost(new URI(req.getRequestURL() + "")) + "/upload/" + fileName;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("上传文件失败");
        }
        return ResultGenerator.genSuccessResultData(temp);
    }
}
