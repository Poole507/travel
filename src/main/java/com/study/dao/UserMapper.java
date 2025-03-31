package com.study.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    @Select("select count(*) from user where name  NOT IN (  #{name} )")
   Integer selectUnRepeatUserNums(@Param("name") String name);
}
