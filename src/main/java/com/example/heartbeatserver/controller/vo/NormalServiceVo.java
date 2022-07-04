package com.example.heartbeatserver.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class NormalServiceVo {

    private Integer ServiceType;

    private String ServiceName;

    private List<ServiceItemVo> ServiceItemVo;
}
