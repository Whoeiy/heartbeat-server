package com.example.heartbeatserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Gift {

    private Integer giftId;

    private String giftName;

    private String giftIntro;

    private Double originalPrice;

    private Double vipPrice;

    private Integer stockNum;

    private Integer giftThirdCategoryId;

    private String giftLabelIdList;

    private Integer isShown;

    private String imgUrl;

    private String giftDetail;

    private Integer vendorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer isDeleted;
}