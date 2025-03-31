package com.study.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.entity.Orders;
import com.study.entity.Orders_item;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface Orders_itemMapper extends BaseMapper<Orders_item> {
    @Select("select * from orders where order_num=#{id}")
    Orders selectOrdersByNums(@Param("id") String id);
}
