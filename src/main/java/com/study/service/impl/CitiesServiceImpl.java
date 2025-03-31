package com.study.service.impl;

import com.study.entity.Cities;
import com.study.dao.CitiesMapper;
import com.study.service.CitiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class CitiesServiceImpl extends ServiceImpl<CitiesMapper, Cities> implements CitiesService {

}
