package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.FoodMapper;
import com.study.entity.Food;
import com.study.service.FoodService;
import org.springframework.stereotype.Service;


@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
}
