
package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.dao.ProvincesMapper;
import com.study.entity.*;
import com.study.service.GoodsService;
import com.study.service.ShopcartService;
import javafx.application.HostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class RoomorderController {
   
    @Autowired
    private com.study.service.RoomorderService RoomorderService;
    @Autowired
    private GoodsService GoodsService;
    @Autowired
    private ShopcartService ShopcartService;
    @Autowired
    private com.study.service.UserService UserService;

    @Autowired
    private com.study.service.HotelService HotelService;

    @RequestMapping("/deleteRoomorder")
    @ResponseBody
    public String deleteRoomorder(Integer id){
        String message = "no";
        RoomorderService.removeById(id);
        message = "yes";
        return message;
    }

    @RequestMapping("addRoomorder")
    public String addRoomorder(Roomorder data,Integer[] goodslist, Model model, HttpServletRequest request, HttpSession httpSession){
        User User = (User)httpSession.getAttribute("user");//当前登录人
        data.setCtime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        data.setUser_id(User.getId());
        data.setRno(getRno());
        RoomorderService.save(data);
        return "redirect:/RoomorderList";
    }
    public String getRno() {//生成订单号
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;

    }

    //查询
    @RequestMapping("/RoomorderList")
    public String RoomorderList( HttpSession httpSession,Model model){
        QueryWrapper<Roomorder> queryWrapper = new QueryWrapper<Roomorder>();
        User User = (User)httpSession.getAttribute("user");
        if(User!=null){
            queryWrapper.eq("user_id",User.getId());
        }
        List<Roomorder> list = RoomorderService.list(queryWrapper);
        for(Roomorder data:list){
            data.setUser(UserService.getById(data.getUser_id()));
            data.setHotel(HotelService.getById(data.getHotel_id()));
        }
        model.addAttribute("list",list);
        return "roomorder/list";
    }


}

