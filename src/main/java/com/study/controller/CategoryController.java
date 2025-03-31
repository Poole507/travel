package com.study.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.entity.Category;
import com.study.entity.Category;
import com.study.entity.User;
import com.study.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CategoryController {


    @Autowired
    private CategoryService CategoryService;

    //删除分类
    @RequestMapping("/deleteCategory")
    @ResponseBody
    public String deleteCategory(Integer id, Model model, HttpSession httpSession){
        String message = "no";
        CategoryService.removeById(id);
        message = "yes";
        return message;
    }

    //新增分类页面
    @RequestMapping("/toAddCategory")
    public String toAddCategorys( HttpServletRequest request){
        return "category/add";
    }

    //新增分类
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(Category data, HttpSession httpSession, Model model) throws UnsupportedEncodingException {
        CategoryService.save(data);
        return "redirect:/CategoryList";
    }

    //修改分类
    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    public String updateCategory(Category data) throws UnsupportedEncodingException {
        CategoryService.updateById(data);
        return "redirect:/CategoryList";
    }

    //修改页面
    @RequestMapping("/toUpdateCategory")
    public String toUpdateCategory(Integer id, Model model){
        Category data =CategoryService.getById(id);
        model.addAttribute("data",data);
        return "category/update";
    }

    //查询分类
    @RequestMapping("/CategoryList")
    public String CategoryList(Model model){
        List<Category> list = CategoryService.list();
        model.addAttribute("list",list);
        return "category/list";
    }



}
