package com.example.heartbeatserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "gift", createIndex = true)
public class EsGift {
    @Id
    @Field(type = FieldType.Integer)
    private Integer giftId;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String giftName;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String giftIntro;

//    @Field(type = FieldType.Double)
    private Double originalPrice;

//    @Field(type = FieldType.Double)
    private Double vipPrice;

//    @Field(type = FieldType.Integer)
    private Integer stockNum;

    @Field(type = FieldType.Nested)
    private List<EsCategory> giftCategory;

    @Field(type = FieldType.Nested)
    private List<List<EsLabel>> giftLabelList;
//    private String giftLabelList;

//    @Field(type = FieldType.Integer)
    private Integer isShown;

//    @Field(type = FieldType.Text)
    private String imgUrl;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String giftDetail;

//    @Field(type = FieldType.Integer)
    private Integer vendorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @Field(type = FieldType.Date)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @Field(type = FieldType.Date)
    private Date updateTime;

//    @Field(type = FieldType.Integer)
    private Integer isDeleted;
}
