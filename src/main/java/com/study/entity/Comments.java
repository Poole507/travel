package com.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("comments")
public class Comments {

    @TableId(type = IdType.AUTO)//将其指定为主键
    private Integer id;
    private Integer user_id;
    private Integer goods_id;
    private String content;
    private String create_time;
    @TableField(exist = false)//非数据库的字段
    User user;
    @TableField(exist = false)//非数据库的字段
    Goods goods;

}
