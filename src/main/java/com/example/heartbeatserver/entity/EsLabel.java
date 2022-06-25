package com.example.heartbeatserver.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class EsLabel {

    private Integer labelId;

    private Integer labelLevel;

    @Field(type = FieldType.Text)
    private String labelName;

    private String labelIcon;

    private Integer parentId;
}
