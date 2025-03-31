package com.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("Hotel")
public class Hotel {

    @TableId(type = IdType.AUTO)//将其指定为主键
    private Integer id;
    private String names;
    private String address;
    private String detail;
    private String phone;
    private Integer is_deleted;
    private String photo;






}
