package com.example.heartbeatserver.util;

import com.github.pagehelper.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageParam{

    private Integer pageNum = 1;

    private Integer pageSize = 10;

}
