package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.BannerMapper;
import com.study.entity.Banner;
import com.study.service.BannerService;
import org.springframework.stereotype.Service;


@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
}
