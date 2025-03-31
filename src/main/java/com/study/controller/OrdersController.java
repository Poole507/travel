
package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.dao.ProvincesMapper;
import com.study.entity.*;
import com.study.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class OrdersController {
    @Autowired
    private ProvincesMapper ProvincesMapper;
    @Autowired
    private com.study.service.OrdersService OrdersService;
    @Autowired
    private GoodsService GoodsService;
    @Autowired
    private ShopcartService ShopcartService;
    @Autowired
    private com.study.service.UserService UserService;
    @Autowired
    private Orders_itemService Orders_itemService;

    @RequestMapping("/deleteOrders")
    @ResponseBody
    public String deleteOrders(Integer id) {
        String message = "no";
        OrdersService.removeById(id);
        message = "yes";
        return message;
    }

    @RequestMapping("addOrders")
    public String addOrders(Orders data, Integer[] goodslist, Model model, HttpServletRequest request, HttpSession httpSession) {
        User User = (User) httpSession.getAttribute("user");//当前登录人
        data.setOrder_num(getRno());
        data.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        data.setUser_id(User.getId());
        data.setTotal(goodslist.length);
        OrdersService.save(data);
        for (Integer i : goodslist) {
            //收藏的每一项
            Shopcart cart = ShopcartService.getById(i);
            //对收藏内景点做出详情订单信息，对应不同商家的订单
            Orders_item orders_item = new Orders_item();
            Goods goods = GoodsService.getById(cart.getGoods_id());
            orders_item.setGoods_id(cart.getGoods_id());
            orders_item.setOrders_ordernum(data.getOrder_num());
            orders_item.setNums(cart.getNums());
            orders_item.setUser_id(data.getUser_id());
            Orders_itemService.save(orders_item);
            goods.setSalenums(goods.getSalenums() + 1);
            GoodsService.updateById(goods);
            ShopcartService.removeById(cart.getId());
        }
        return "redirect:/OrdersList";
    }

    public String getRno() {//生成订单号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;

    }

    //查询
    @RequestMapping("/OrdersList")
    public String OrdersList(HttpSession httpSession, Model model) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<Orders>();
        User User = (User) httpSession.getAttribute("user");
        if (User != null) {
            queryWrapper.eq("user_id", User.getId());
        }
        List<Orders> list = OrdersService.list(queryWrapper);
        for (Orders data : list) {
            data.setUser(UserService.getById(data.getUser_id()));
        }
        model.addAttribute("list", list);
        return "orders/list";
    }

    @RequestMapping("/updateOrders")
    @ResponseBody
    public String updateOrders(Orders orders) {
        String message = "no";
        Orders orders1 = OrdersService.getById(orders.getId());
        QueryWrapper<Orders_item> queryWrapper = new QueryWrapper<Orders_item>();
        queryWrapper.eq("user_id", orders1.getUser_id());
        queryWrapper.eq("orders_ordernum", orders1.getOrder_num());
        List<Orders_item> Orders_item_list = Orders_itemService.list(queryWrapper);
        for (Orders_item data : Orders_item_list) {
            data.setState(2);
            Orders_itemService.updateById(data);
        }
        OrdersService.updateById(orders);
        message = "yes";
        return message;
    }
}

