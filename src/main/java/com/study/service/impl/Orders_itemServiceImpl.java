package com.study.service.impl;

import com.study.dao.OrdersMapper;
import com.study.entity.Orders;
import com.study.entity.Orders_item;
import com.study.dao.Orders_itemMapper;
import com.study.service.Orders_itemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Orders_itemServiceImpl extends ServiceImpl<Orders_itemMapper, Orders_item> implements Orders_itemService {
    @Autowired
    private Orders_itemMapper Orders_itemMapper;
    @Override
    public Orders selectOrdersByNums(String id) {
        return  Orders_itemMapper.selectOrdersByNums(id);
    }
}
