package com.study.controller;


import com.study.entity.Banner;
import com.study.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class BannerController {


    @Autowired
    private BannerService BannerService;

    //删除轮播
    @RequestMapping("/deleteBanner")
    @ResponseBody
    public String deleteBanner(Integer id, Model model, HttpSession httpSession) {
        String message = "no";
        BannerService.removeById(id);
        message = "yes";
        return message;
    }

    //新增轮播页面
    @RequestMapping("/toAddBanner")
    public String toAddBanners(HttpServletRequest request) {
        return "banner/add";
    }

    //新增轮播
    @RequestMapping(value = "/addBanner", method = RequestMethod.POST)
    public String addBanner(Banner data, HttpSession httpSession, Model model) throws UnsupportedEncodingException {
        BannerService.save(data);
        return "redirect:/BannerList";
    }

    //修改轮播
    @RequestMapping(value = "/updateBanner", method = RequestMethod.POST)
    public String updateBanner(Banner data) throws UnsupportedEncodingException {
        BannerService.updateById(data);
        return "redirect:/BannerList";
    }

    //修改页面
    @RequestMapping("/toUpdateBanner")
    public String toUpdateBanner(Integer id, Model model) {
        Banner data = BannerService.getById(id);
        model.addAttribute("data", data);
        return "banner/update";
    }

    //查询轮播
    @RequestMapping("/BannerList")
    public String BannerList(Model model) {
        List<Banner> list = BannerService.list();
        model.addAttribute("list", list);
        return "banner/list";
    }


}
