
package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.dao.ProvincesMapper;
import com.study.entity.*;
import com.study.entity.User;

import com.study.service.CategoryService;
import com.study.service.GoodsService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {
    @Autowired
    private ProvincesMapper ProvincesMapper;
    @Autowired
    private CategoryService CategoryService;
    @Autowired
    private GoodsService GoodsService;


    @RequestMapping("findAreaByCityId")
    @ResponseBody
    public List<Areas> findAreaByCityId(String city_Id){
        return ProvincesMapper.findAreasByCityId(city_Id);
    }
    @RequestMapping("findCityByProId")
    @ResponseBody
    public List<Cities> findCityByProId(String province_Id){
        return ProvincesMapper.findCitiesByProvinceId(province_Id);
    }
    //删除Goods
    @RequestMapping("/deleteGoods")
    @ResponseBody
    public String deleteGoods(Integer id){
        String message = "no";
        GoodsService.removeById(id);
        message = "yes";
        return message;
    }
    //修改
    @RequestMapping(value = "/updateGoods", method = RequestMethod.POST)
    public String updateGoods(Goods data) throws UnsupportedEncodingException {
        GoodsService.updateById(data);
        return "redirect:/GoodsList";
    }


    //添加
    @RequestMapping(value = "/addGoods", method = RequestMethod.POST)
    public String addGoods( HttpSession httpSession,Goods data) throws UnsupportedEncodingException {

        data.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        GoodsService.save(data);
        return "redirect:/GoodsList";
    }
    //设置热门
    @RequestMapping(value = "/setHot")
    @ResponseBody
    public String setHot(Goods data) {
        String message = "no";
        GoodsService.updateById(data);
        message = "yes";
        return message;
    }
    //查询
    @RequestMapping("/GoodsList")
    public String GoodsList( HttpSession httpSession,Model model){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<Goods>();

        List<Goods> list = GoodsService.list(queryWrapper);
        for(Goods data:list){
            data.setCategory(CategoryService.getById(data.getCategory_id()));
            data.setProvinces(ProvincesMapper.findProvinceById(data.getProvince_Id().toString()));
            data.setCities(ProvincesMapper.findCityById(data.getCity_Id().toString()));
            data.setAreas(ProvincesMapper.findAreaById(data.getArea_Id().toString()));
        }
       model.addAttribute("list",list);
       return "goods/list";
    }

    //访问添加页面
    @RequestMapping("toAddGoods")
    public String toAddGoods(Model model){
        List<Category> categorylist = CategoryService.list();
        List<Provinces> list = ProvincesMapper.findAllProvince();
        model.addAttribute("list",list);
        model.addAttribute("categorylist",categorylist);
        return "goods/add";
    }

    //访问修改页面
    @RequestMapping("toUpdateGoods")
    public String toUpdateGoods(Integer id, Model model){
        Goods data =GoodsService.getById(id);
        List<Provinces> list=ProvincesMapper.findAllProvince();
        List<Areas> arealist=ProvincesMapper.findAreasByCityId(data.getCity_Id());
        List<Cities> citielist=ProvincesMapper.findCitiesByProvinceId(data.getProvince_Id());
        List<Category> categorylist = CategoryService.list();
        model.addAttribute("categorylist",categorylist);
        model.addAttribute("list",list);
        model.addAttribute("arealist",arealist);
        model.addAttribute("citylist",citielist);
        model.addAttribute("data",data);
        return "goods/update";
    }

}

