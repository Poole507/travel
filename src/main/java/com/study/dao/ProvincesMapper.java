package com.study.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.entity.Areas;
import com.study.entity.Cities;
import com.study.entity.Provinces;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ProvincesMapper extends BaseMapper<Provinces> {

    @Select("select * from provinces")
    List<Provinces> findAllProvince();
    @Select("select * from cities")
    List<Cities> findAllCities();
    @Select("select * from areas")
    List<Areas> findAllAreas();

    @Select("select * from cities where province_id=#{id}")
    List<Cities> findCitiesByProvinceId(@Param("id") String provinceId);
    @Select("select * from areas where city_id=#{id}")
    List<Areas> findAreasByCityId(@Param("id") String cityId);

    @Select("select * from areas where area_id=#{id}")
    Areas findAreaById(@Param("id") String areaId);

    @Select("select * from provinces where province_id=#{id}")
    Provinces findProvinceById(@Param("id") String provincesId);

    @Select("select * from cities where city_id=#{id}")
    Cities findCityById(@Param("id") String cityId);

}
