package com.example.heartbeatserver.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class SecondLevelLabel {
    private Integer labelId;
    private Integer parentId;
    private Integer labelLevel;
    private String labelName;
    private List<ThirdLevelLabel> thirdLevelLabelVOS;
}
