package com.study.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;


@Data
@TableName("orders")
public class Orders {

    @TableId(type = IdType.AUTO)//将其指定为主键
    private Integer id;
    private Integer user_id;

    @TableField(exist = false)//非数据库的字段
    User user;
    private Integer total;
    private double money;
    private String order_num;
    private String create_time;
    private String username;
    private String phone;
    private Integer pay_state;
    private String content;

    @TableLogic
    private Integer is_deleted;

}
