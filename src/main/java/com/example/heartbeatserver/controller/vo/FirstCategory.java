package com.example.heartbeatserver.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class FirstCategory {
    private Integer categoryId;
    private Integer categoryLevel;
    private String categoryName;
    private List<SecondLevelCategory> secondLevelCategoryVOS;


}
