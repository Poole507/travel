package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.ShopcartMapper;
import com.study.entity.Shopcart;
import com.study.service.ShopcartService;
import org.springframework.stereotype.Service;


@Service
public class ShopcartServiceImpl extends ServiceImpl<ShopcartMapper, Shopcart> implements ShopcartService {
}
