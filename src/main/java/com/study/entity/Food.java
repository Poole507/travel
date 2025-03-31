package com.study.entity;



import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("food")
public class Food {
    @TableId(type = IdType.AUTO)   //用于说明谁是主键
    private Integer id;
    private String province_Id;
    private String city_Id;
    private String area_Id;
    private String names;
    private String createtime;
    private String address;
    private String detail;
    private double price;
    private String photo;
    @TableField(exist = false)//非数据库的字段
    Provinces provinces;
    @TableField(exist = false)//非数据库的字段
    Cities cities;
    @TableField(exist = false)//非数据库的字段
    Areas areas;

    @TableLogic
    private Integer is_deleted;
    private String content;


}