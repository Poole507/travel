
package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.dao.ProvincesMapper;
import com.study.entity.Comments;
import com.study.entity.Goods;
import com.study.entity.User;
import com.study.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CommentsController {


    @Autowired
    private GoodsService GoodsService;
    @Autowired
    private ProvincesMapper ProvincesMapper;
    @Autowired
    private com.study.service.UserService UserService;
    @Autowired
    private CommentsService CommentsService;
    @Autowired
    private CategoryService CategoryService;
    @Autowired
    private BannerService BannerService;
    @Autowired
    private BoardService BoardService;

    //删除Comments
    @RequestMapping("/deleteComments")
    @ResponseBody
    public String deleteComments(Integer id) {
        String message = "no";
        CommentsService.removeById(id);
        message = "yes";
        return message;
    }

    //添加
    @RequestMapping(value = "/addComments")
    @ResponseBody
    public String addComments(HttpSession httpSession, Comments data) {
        User User = (User) httpSession.getAttribute("user");
        String message = "no";
        data.setCreate_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        data.setUser_id(User.getId());
        CommentsService.save(data);
        message = "yes";
        return message;
    }

    //查询
    @RequestMapping("/CommentsList")
    public String CommentsList(HttpSession httpSession, Model model) {
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<Comments>();
        User User = (User) httpSession.getAttribute("user");
        if (User != null) {
            queryWrapper.eq("user_id", User.getId());
        }
        List<Comments> list = CommentsService.list(queryWrapper);
        for (Comments data : list) {
            data.setUser(UserService.getById(data.getUser_id()));
            data.setGoods(GoodsService.getById(data.getGoods_id()));
        }
        model.addAttribute("list", list);
        return "comments/list";
    }

    @RequestMapping("/toAddComments")
    public String toAddComments(HttpSession httpSession, Model model, Goods data) {
        Goods goods = GoodsService.getById(data.getId());
        goods.setCategory(CategoryService.getById(goods.getCategory_id()));
        goods.setProvinces(ProvincesMapper.findProvinceById(goods.getProvince_Id().toString()));
        goods.setCities(ProvincesMapper.findCityById(goods.getCity_Id().toString()));
        goods.setAreas(ProvincesMapper.findAreaById(goods.getArea_Id().toString()));
        QueryWrapper<Comments> queryWrapperComments = new QueryWrapper<Comments>();
        queryWrapperComments.eq("goods_id", goods.getId());
        List<Comments> commentsList = CommentsService.list(queryWrapperComments);//全部的景点类型
        for (Comments commentsdata : commentsList) {
            commentsdata.setUser(UserService.getById(commentsdata.getUser_id()));
            commentsdata.setGoods(GoodsService.getById(commentsdata.getGoods_id()));
        }
        model.addAttribute("commentsList", commentsList);

        model.addAttribute("goods", goods);
        model.addAttribute("canaddcomments", 1);
        return "index/goods_detail";
    }


}

