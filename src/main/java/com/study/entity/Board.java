package com.study.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("Board")
public class Board {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String detail;
    private String editor;
    private String photo;
    private String create_time;
    @TableLogic
    private Integer is_deleted;

}
