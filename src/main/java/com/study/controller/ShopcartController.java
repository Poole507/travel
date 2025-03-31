
package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.dao.ProvincesMapper;
import com.study.entity.Goods;
import com.study.entity.Provinces;
import com.study.entity.Shopcart;
import com.study.entity.User;
import com.study.service.ShopcartService;
import com.study.service.GoodsService;
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

import static com.oracle.jrockit.jfr.ContentType.Address;

@Controller
public class ShopcartController {
    @Autowired
    private ProvincesMapper ProvincesMapper;

    @Autowired
    private GoodsService GoodsService;
    @Autowired
    private ShopcartService ShopcartService;
    @Autowired
    private com.study.service.UserService UserService;

    //删除Shopcart
    @RequestMapping("/deleteShopcart")
    @ResponseBody
    public String deleteShopcart(Integer id) {
        String message = "no";
        ShopcartService.removeById(id);
        message = "yes";
        return message;
    }

    @RequestMapping("reduceCartNum")
    @ResponseBody
    public String reduceCartNum(Integer cartId) {
        Shopcart Shopcart = ShopcartService.getById(cartId);
        Shopcart.setNums(Shopcart.getNums() - 1);
        ShopcartService.updateById(Shopcart);
        return "success";
    }

    @RequestMapping("addCartNum")
    @ResponseBody
    public String addCartNum(Integer cartId) {
        Shopcart Shopcart = ShopcartService.getById(cartId);
        Shopcart.setNums(Shopcart.getNums() + 1);
        ShopcartService.updateById(Shopcart);
        return "success";
    }

    //添加
    @RequestMapping(value = "/addShopcart")
    @ResponseBody
    public String addShopcart(HttpSession httpSession, Shopcart data) {
        User User = (User) httpSession.getAttribute("user");
        String message = "no";
        if (User != null) {
            QueryWrapper<Shopcart> queryWrapper = new QueryWrapper<Shopcart>();
            queryWrapper.eq("user_id", User.getId());
            queryWrapper.eq("goods_id", data.getGoods_id());
            Shopcart shopcartIfRepeat = ShopcartService.getOne(queryWrapper);
            if (shopcartIfRepeat == null) {
                data.setUser_id(User.getId());
                ShopcartService.save(data);
                Goods goods = GoodsService.getById(data.getGoods_id());
                goods.setCollectnums(goods.getCollectnums() + 1);
                GoodsService.updateById(goods);
                message = "yes";
                return message;
            } else {//代表已经加入了这个，这个时候只需要修改数量
                shopcartIfRepeat.setNums(shopcartIfRepeat.getNums() + data.getNums());
                ShopcartService.updateById(shopcartIfRepeat);
                message = "yes";
                return message;
            }
        } else {
            return message;
        }

    }

    @RequestMapping("preOrder")
    public String preOrder(Integer[] goodslist, Model model, HttpServletRequest request, HttpSession httpSession) {
        List<Shopcart> cartList = new ArrayList<Shopcart>();
        for (Integer i : goodslist) {
            Shopcart cart = ShopcartService.getById(i);
            cartList.add(cart);
        }
        Double total = 0.0;
        for (Shopcart data : cartList) {
            data.setGoods(GoodsService.getById(data.getGoods_id()));
            total += (data.getNums() * data.getGoods().getPrice());
        }

        User User = (User) httpSession.getAttribute("user");

        List<Provinces> list = ProvincesMapper.findAllProvince();
        model.addAttribute("list", list);
        model.addAttribute("cartList", cartList);
        model.addAttribute("total", total);
        return "index/confirm_order";
    }

    @RequestMapping("/findCartByUser")
    @ResponseBody
    //Ajax请求的数据
    public List<Shopcart> findCartByUser(HttpSession httpSession, HttpServletRequest request) {
        QueryWrapper<Shopcart> queryWrapper = new QueryWrapper<Shopcart>();
        User User = (User) httpSession.getAttribute("user");
        if (User != null) {
            queryWrapper.eq("user_id", User.getId());
        }
        List<Shopcart> list = ShopcartService.list(queryWrapper);
        for (Shopcart data : list) {
            data.setGoods(GoodsService.getById(data.getGoods_id()));
        }
        return list;
    }

    //查询
    @RequestMapping("/ShopcartList")
    public String ShopcartList(HttpSession httpSession, Model model) {
        QueryWrapper<Shopcart> queryWrapper = new QueryWrapper<Shopcart>();
        User User = (User) httpSession.getAttribute("user");
        if (User != null) {
            queryWrapper.eq("user_id", User.getId());
        }
        List<Shopcart> list = ShopcartService.list(queryWrapper);
        Double totalmoney = 0.0;
        model.addAttribute("list", list);
        return "index/shopcart";
    }


}

