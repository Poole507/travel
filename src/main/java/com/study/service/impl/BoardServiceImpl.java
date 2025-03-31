package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.BoardMapper;
import com.study.entity.Board;
import com.study.service.BoardService;
import org.springframework.stereotype.Service;


@Service
public class BoardServiceImpl extends ServiceImpl<BoardMapper, Board> implements BoardService {
}
