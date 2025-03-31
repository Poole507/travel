package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.HotelMapper;
import com.study.entity.Hotel;
import com.study.service.HotelService;
import org.springframework.stereotype.Service;


@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements HotelService {
}
