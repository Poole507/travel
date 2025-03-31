package com.study.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("banner")
public class Banner {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String photo;
    private String title;
    @TableLogic
    private Integer is_deleted;
}
