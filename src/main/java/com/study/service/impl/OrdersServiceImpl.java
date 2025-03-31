package com.study.service.impl;

import com.study.entity.Orders;
import com.study.dao.OrdersMapper;
import com.study.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private OrdersMapper OrdersMapper;
    @Override
    public Orders selectOrdersByOrders_num(String id) {
     return  OrdersMapper.selectOrdersByOrders_num(id);
    }
}
