package com.study.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("Category")
public class Category {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String photo;
    @TableLogic
    private Integer is_deleted;

    @TableField(exist = false)//非数据库的字段
    Integer nums;//景点数
}
