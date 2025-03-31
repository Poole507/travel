package com.study.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.Orders;
import com.study.entity.Orders_item;
import com.study.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class Orders_itemController {
    @Autowired
    private OrdersService OrdersService;
    @Autowired
    private GoodsService GoodsService;
    @Autowired
    private ShopcartService ShopcartService;
    @Autowired
    private UserService UserService;
    @Autowired
    private Orders_itemService Orders_itemService;

    @RequestMapping("/deleteOrders_item")
    @ResponseBody
    public String deleteOrders_item(Integer id){
        String message = "no";
        Orders_itemService.removeById(id);
        message = "yes";
        return message;
    }
    //发货的操作
    @RequestMapping("/updateOrders_item")
    public String updateOrders_item(Orders_item data){
        String message = "no";
        Orders_item orders_item=Orders_itemService.getById(data.getId());

        orders_item.setState(3);
        Orders_itemService.updateById(orders_item);
        message = "yes";
        return "redirect:/Orders_itemList";
    }
    //只更新订单状态
    @RequestMapping("/updateOrders_itemState")
    @ResponseBody
    public String updateOrders_itemState(Orders_item data){
        String message = "no";
        Orders_itemService.updateById(data);
        message = "yes";
        return message;
    }
    @RequestMapping("/selectOrders_itemList")
    public String selectOrders_itemList(HttpSession httpSession, Model model,String orders_ordernum){
        QueryWrapper<Orders_item> queryWrapper = new QueryWrapper<Orders_item>();
        queryWrapper.eq("orders_ordernum",orders_ordernum);
        List<Orders_item> list = Orders_itemService.list(queryWrapper);
        for(Orders_item data:list){
            data.setUser(UserService.getById(data.getUser_id()));
            data.setOrders(OrdersService.selectOrdersByOrders_num(data.getOrders_ordernum()));
            data.setGoods(GoodsService.getById(data.getGoods_id()));
        }
        model.addAttribute("list",list);
        return "orders_item/select_list";
    }
    @RequestMapping("/Orders_itemList")
    public String Orders_itemList(HttpSession httpSession, Model model,String orders_ordernum){
        QueryWrapper<Orders_item> queryWrapper = new QueryWrapper<Orders_item>();

        List<Orders_item> list = Orders_itemService.list(queryWrapper);
        for(Orders_item data:list){
            data.setUser(UserService.getById(data.getUser_id()));
            data.setOrders(OrdersService.selectOrdersByOrders_num(data.getOrders_ordernum()));
            data.setGoods(GoodsService.getById(data.getGoods_id()));
        }
        model.addAttribute("list",list);
        return "orders_item/list";
    }
}

