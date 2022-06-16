package com.example.heartbeatserver.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class FirstLabel {
    private Integer labelId;
    private Integer labelLevel;
    private String labelName;
    private List<SecondLevelLabel> secondLevelLabelVOS;

}
