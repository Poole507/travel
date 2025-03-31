package com.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Cities")
public class Cities {
	@TableId(type = IdType.AUTO)
	private String city_Id;
	private String city_Name;
	private String province_Id;

}
