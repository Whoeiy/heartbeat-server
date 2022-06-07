package com.example.heartbeatserver.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class SecondLevelCategory {
    private Integer categoryId;
    private Integer parentId;
    private Integer categoryLevel;
    private String categoryName;
    private List<ThirdLevelCategory> thirdLevelCategoryVOS;
}
