package com.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Provinces")
public class Provinces {
	@TableId(type = IdType.AUTO)   //用于说明谁是主键
	private String province_Id;
	private String province_Name;

}
