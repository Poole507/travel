package com.study.service;

import com.study.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.usermodel.Picture;


public interface OrdersService extends IService<Orders> {

    public Orders selectOrdersByOrders_num(String id);

}
