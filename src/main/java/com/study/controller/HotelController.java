
package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.dao.ProvincesMapper;
import com.study.entity.*;
import com.study.service.CategoryService;
import com.study.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HotelController {
    @Autowired
    private ProvincesMapper ProvincesMapper;
    @Autowired
    private CategoryService CategoryService;
    @Autowired
    private HotelService HotelService;


    //删除Hotel
    @RequestMapping("/deleteHotel")
    @ResponseBody
    public String deleteHotel(Integer id){
        String message = "no";
        HotelService.removeById(id);
        message = "yes";
        return message;
    }
    //修改
    @RequestMapping(value = "/updateHotel", method = RequestMethod.POST)
    public String updateHotel(Hotel data) throws UnsupportedEncodingException {
        HotelService.updateById(data);
        return "redirect:/HotelList";
    }


    //添加
    @RequestMapping(value = "/addHotel", method = RequestMethod.POST)
    public String addHotel( HttpSession httpSession,Hotel data) throws UnsupportedEncodingException {

        HotelService.save(data);
        return "redirect:/HotelList";
    }

    //查询
    @RequestMapping("/HotelList")
    public String HotelList( HttpSession httpSession,Model model){
        QueryWrapper<Hotel> queryWrapper = new QueryWrapper<Hotel>();

        List<Hotel> list = HotelService.list(queryWrapper);

       model.addAttribute("list",list);
       return "hotel/list";
    }

    //访问添加页面
    @RequestMapping("toAddHotel")
    public String toAddHotel(Model model){
        List<Category> categorylist = CategoryService.list();
        List<Provinces> list = ProvincesMapper.findAllProvince();
        model.addAttribute("list",list);
        model.addAttribute("categorylist",categorylist);
        return "hotel/add";
    }

    //访问修改页面
    @RequestMapping("toUpdateHotel")
    public String toUpdateHotel(Integer id, Model model){
        Hotel data =HotelService.getById(id);
        List<Category> categorylist = CategoryService.list();
        model.addAttribute("categorylist",categorylist);
        model.addAttribute("data",data);
        return "hotel/update";
    }

}

