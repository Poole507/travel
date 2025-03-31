package com.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Areas")
public class Areas {

@TableId(type = IdType.AUTO)//将其指定为主键
	private String area_Id;
	private String area_Name;
	private String city_Id;

	public String getArea_Id() {
		return area_Id;
	}

	public void setArea_Id(String area_Id) {
		this.area_Id = area_Id;
	}

	public String getArea_Name() {
		return area_Name;
	}

	public void setArea_Name(String area_Name) {
		this.area_Name = area_Name;
	}

	public String getCity_Id() {
		return city_Id;
	}

	public void setCity_Id(String city_Id) {
		this.city_Id = city_Id;
	}
}
