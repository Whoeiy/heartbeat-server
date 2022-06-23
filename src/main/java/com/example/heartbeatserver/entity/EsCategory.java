package com.example.heartbeatserver.entity;

import lombok.Data;

@Data
public class EsCategory {

    private Integer categoryId;

    private Integer categoryLevel;

    private String categoryName;

    private String categoryIcon;

    private Integer parentId;
}
