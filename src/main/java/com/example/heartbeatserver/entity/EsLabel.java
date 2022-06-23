package com.example.heartbeatserver.entity;

import lombok.Data;

@Data
public class EsLabel {

    private Integer labelId;

    private Integer labelLevel;

    private String labelName;

    private String labelIcon;

    private Integer parentId;
}
