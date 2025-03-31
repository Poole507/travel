
package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.dao.ProvincesMapper;
import com.study.entity.*;
import com.study.service.CategoryService;
import com.study.service.FoodService;
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
public class FoodController {
    @Autowired
    private ProvincesMapper ProvincesMapper;
    @Autowired
    private CategoryService CategoryService;
    @Autowired
    private FoodService FoodService;



    //删除Food
    @RequestMapping("/deleteFood")
    @ResponseBody
    public String deleteFood(Integer id){
        String message = "no";
        FoodService.removeById(id);
        message = "yes";
        return message;
    }
    //修改
    @RequestMapping(value = "/updateFood", method = RequestMethod.POST)
    public String updateFood(Food data) throws UnsupportedEncodingException {
        FoodService.updateById(data);
        return "redirect:/FoodList";
    }


    //添加
    @RequestMapping(value = "/addFood", method = RequestMethod.POST)
    public String addFood( HttpSession httpSession,Food data) throws UnsupportedEncodingException {

        data.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        FoodService.save(data);
        return "redirect:/FoodList";
    }

    //查询
    @RequestMapping("/FoodList")
    public String FoodList( HttpSession httpSession,Model model){
        QueryWrapper<Food> queryWrapper = new QueryWrapper<Food>();

        List<Food> list = FoodService.list(queryWrapper);
        for(Food data:list){
            data.setProvinces(ProvincesMapper.findProvinceById(data.getProvince_Id().toString()));
            data.setCities(ProvincesMapper.findCityById(data.getCity_Id().toString()));
            data.setAreas(ProvincesMapper.findAreaById(data.getArea_Id().toString()));
        }
       model.addAttribute("list",list);
       return "food/list";
    }

    //访问添加页面
    @RequestMapping("toAddFood")
    public String toAddFood(Model model){
        List<Category> categorylist = CategoryService.list();
        List<Provinces> list = ProvincesMapper.findAllProvince();
        model.addAttribute("list",list);
        model.addAttribute("categorylist",categorylist);
        return "food/add";
    }

    //访问修改页面
    @RequestMapping("toUpdateFood")
    public String toUpdateFood(Integer id, Model model){
        Food data =FoodService.getById(id);
        List<Provinces> list=ProvincesMapper.findAllProvince();
        List<Areas> arealist=ProvincesMapper.findAreasByCityId(data.getCity_Id());
        List<Cities> citielist=ProvincesMapper.findCitiesByProvinceId(data.getProvince_Id());
        List<Category> categorylist = CategoryService.list();
        model.addAttribute("categorylist",categorylist);
        model.addAttribute("list",list);
        model.addAttribute("arealist",arealist);
        model.addAttribute("citylist",citielist);
        model.addAttribute("data",data);
        return "food/update";
    }

}

