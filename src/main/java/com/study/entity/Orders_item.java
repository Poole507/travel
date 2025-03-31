package com.study.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

    @Data
    @TableName("orders_item")
    public class Orders_item {


  @TableId(type = IdType.AUTO)//将其指定为主键
    private Integer id;
    private String orders_ordernum;
    private Integer goods_id;
        private Integer user_id;
    private Integer nums;
    private Integer state;
        @TableField(exist = false)//非数据库的字段
    Orders orders;
        @TableField(exist = false)//非数据库的字段
                User user;
        @TableField(exist = false)//非数据库的字段
    Goods goods;


        @TableLogic
        private Integer is_deleted;

}
