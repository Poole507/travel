package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.CommentsMapper;
import com.study.entity.Comments;
import com.study.service.CommentsService;
import org.springframework.stereotype.Service;


@Service
public class CommetnsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {
}
