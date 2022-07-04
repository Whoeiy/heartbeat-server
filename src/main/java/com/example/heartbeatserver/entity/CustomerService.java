package com.example.heartbeatserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerService implements  Serializable{

    private Integer serviceId;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务样图url
     */
    private String sampleImg;

    /**
     * 服务类型: 1 - 定制卡片 2 - 定制包装 3 - 定制外观 4 - 商家特供
     */
    private Integer serviceType;

    /**
     * 排序值
     */
    private Integer showRank;

    /**
     * 是否显示: 0 - 不显示 1 - 显示
     */
    private Integer isShown;

    /**
     * 创建商家Id
     */
    private Integer vendorId;

    /**
     * 是否已删除: 0 - 未删除 1 - 已删除
     */
    private Integer isDeleted;
}
