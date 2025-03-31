package com.study.entity;



import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("Goods")
public class Goods {
    @TableId(type = IdType.AUTO)   //用于说明谁是主键
    private Integer id;
    private String province_Id;
    private String city_Id;
    private String area_Id;
    private Integer numbers;
    private String names;
    private String createtime;
    private String detail;
    private double price;
    private String address;
    private String photo;
    private Integer category_id;
    @TableField(exist = false)//非数据库的字段
    Provinces provinces;
    @TableField(exist = false)//非数据库的字段
    Cities cities;
    @TableField(exist = false)//非数据库的字段
    Areas areas;
    @TableField(exist = false)//非数据库的字段
    Category category;
    private Integer ishot;
    private Integer seenums;

    @TableLogic
    private Integer is_deleted;
    private Integer salenums;
    private double normalprice;
    private Integer collectnums;
    private String content;

}