package com.study.service;

import com.study.entity.Orders;
import com.study.entity.Orders_item;
import com.baomidou.mybatisplus.extension.service.IService;

public interface Orders_itemService extends IService<Orders_item> {

    public Orders selectOrdersByNums(String id);
}
