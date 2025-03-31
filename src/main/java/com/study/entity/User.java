package com.study.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)//设置主键自动递增
    private Integer id;
    private String name;
    private String photo;
    private String email;
    private String sex;
    private String phone;
    private String ctime;
    private Integer age;
    private String pwd;
    private String realname;
    @TableLogic
    private Integer is_deleted;

}
