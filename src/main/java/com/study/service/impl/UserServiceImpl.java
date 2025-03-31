package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.UserMapper;
import com.study.entity.User;
import com.study.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {
}
