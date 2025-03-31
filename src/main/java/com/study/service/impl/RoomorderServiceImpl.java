package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.RoomorderMapper;
import com.study.entity.Roomorder;
import com.study.service.RoomorderService;
import org.springframework.stereotype.Service;


@Service
public class RoomorderServiceImpl extends ServiceImpl<RoomorderMapper, Roomorder> implements RoomorderService {
}
