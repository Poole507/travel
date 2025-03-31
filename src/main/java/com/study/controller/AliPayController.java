package com.study.controller;


import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.AlipayOrder;
import com.study.entity.Goods;
import com.study.entity.Orders;
import com.study.entity.User;
import com.study.service.AliPayService;
import com.study.service.GoodsService;
import com.study.service.OrdersService;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class AliPayController {

    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private OrdersService OrdersService;
    @Autowired
    private GoodsService GoodsService;
    @Autowired
    private UserService UserService;

    /**
     * 提交订单
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/orderalipay")
    public String orderalipay(String id) throws AlipayApiException {
        System.out.println("--------进入提交支付----------");
        Orders orders =OrdersService.getById(id);//这个是根据id获取这个订单信息
        //AlipayOrder是根据支付宝的实体来
        System.out.println("--------进入提交支付----------");
        AlipayOrder AlipayOrder = new AlipayOrder();
        AlipayOrder.setOut_trade_no(orders.getOrder_num());//一会儿要根据订单号来做处理
        AlipayOrder.setSubject(orders.getUsername()+"的订单");
        AlipayOrder.setTotal_amount(Double.toString(orders.getMoney()));
        System.out.println("order-----:"+AlipayOrder);
        return aliPayService.aliPay(AlipayOrder);
    }

    /**s
     * 支付成功以后还需要执行一系列的步骤
     * @return
     */
    @GetMapping("/paySuccess")
    public String paySuccess(HttpSession httpSession, Model model)throws UnsupportedEncodingException {
        System.out.println("--------支付成功----------");
        //如果支付成功，订单就要新增到数据库
        //如果取消支付，也要新增订单到数据库，取消订单-状态
        //订单查询能够查出订单状态 信息
        System.out.println("支付成功");
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<Orders>();
        User User = (User)httpSession.getAttribute("user");
        if(User!=null){
            queryWrapper.eq("user_id",User.getId());
        }
        List<Orders> list = OrdersService.list(queryWrapper);
        for(Orders data:list){
            data.setUser(UserService.getById(data.getUser_id()));
        }
        model.addAttribute("list",list);
        return "orders/list";
    }

}
