package com.study.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.entity.Provinces;
import com.study.entity.Statics;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface EchartsMapper extends BaseMapper<Provinces> {



    @Select(" select count(*) as counts ,any_value(goods.category_id),any_value(category.name) as names from goods,category where any_value(goods.category_id)=any_value(category.id) GROUP  BY any_value(category.name)")
    List<Statics> Normal_Echarts1();

    @Select(" SELECT \n" +
            "count(*) as counts,p.province_Name as names\n" +
            "FROM orders_item oi, goods o,provinces p\n" +
            "where oi.goods_id=o.id\n" +
            "and p.province_Id = o.province_Id\n" +
            "GROUP BY names")
    List<Statics> mapsEcharts();


    @Select(" select count(orders.total) as counts ,left(orders.create_time,7) as  names from orders where orders.is_deleted=0 GROUP BY left(orders.create_time,7)")
    List<Statics> Normal_Echarts3();

    @Select(" select count(*) as counts ,left(roomorder.ctime,7) as  names from roomorder where roomorder.is_deleted=0 GROUP BY left(roomorder.ctime,7)")
    List<Statics> Normal_Echarts2();
}
