package com.study.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;


@Data
@TableName("Roomorder")
public class Roomorder {







    @TableId(type = IdType.AUTO)//将其指定为主键
    private Integer id;
    private Integer user_id;

    @TableField(exist = false)//非数据库的字段
    User user;

    @TableField(exist = false)//非数据库的字段
            Hotel hotel;
    private Integer hotel_id;
    private String ctime;
    private String names;
    private String phone;
    private String room;
    private String content;
    private String rno;

    @TableLogic
    private Integer is_deleted;

}
