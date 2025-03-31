package com.study.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface OrdersMapper extends BaseMapper<Orders> {


    @Select("select * from orders where order_num=#{id}")
    Orders selectOrdersByOrders_num(@Param("id") String id);
}
