package com.example.heartbeatserver.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class EsCategory {

    private Integer categoryId;

    private Integer categoryLevel;

    @Field(type = FieldType.Text)
    private String categoryName;

    private String categoryIcon;

    private Integer parentId;
}
