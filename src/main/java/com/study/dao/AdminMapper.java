package com.study.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.entity.Admin;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select count(1) as counts , left(create_time,10) as names   from comments   GROUP BY names order by names desc limit 0,6")
    List<Map<String, Object>> statics1();

    @Select("select count(1) as counts , ca.name as names    from contest  con left join category ca on con.cid = ca.id  GROUP BY names  ")
    List<Map<String, Object>> statics2();

    @Select("select count(1) as counts , left(create_time,10) as names  from news  GROUP BY names")
    List<Map<String, Object>> statics3();


    @Select("select count(1) as counts , left(create_time,10) as names  from board  GROUP BY names")
    List<Map<String, Object>> statics4();
}
