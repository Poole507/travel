package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.ProvincesMapper;
import com.study.entity.*;
import com.study.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {

    @Autowired
    private CommentsService CommentsService;
    @Autowired
    private GoodsService GoodsService;
    @Autowired
    private ProvincesMapper ProvincesMapper;
    @Autowired
    private UserService UserService;
    @Autowired
    private HotelService HotelService;

    @Autowired
    private CategoryService CategoryService;
    @Autowired
    private BannerService BannerService;
    @Autowired
    private BoardService BoardService;
    @Autowired
    private FoodService FoodService;

    @RequestMapping("/toFoodDetail")
    public String toFoodDetail(Model model, Integer id, HttpServletRequest request) {
        Food food = FoodService.getById(id);
        food.setProvinces(ProvincesMapper.findProvinceById(food.getProvince_Id().toString()));
        food.setCities(ProvincesMapper.findCityById(food.getCity_Id().toString()));
        food.setAreas(ProvincesMapper.findAreaById(food.getArea_Id().toString()));
        model.addAttribute("food", food);

        return "index/food_detail";
    }

    @RequestMapping("/toFood")
    public String toFood(Model model, String names,
                         @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                         HttpSession httpSession, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Food> queryWrapper = new QueryWrapper<Food>();
        queryWrapper.like(StringUtils.hasText(names), "names", names);

        List<Food> foodlist = FoodService.list(queryWrapper);
        for (Food data : foodlist) {
            data.setProvinces(ProvincesMapper.findProvinceById(data.getProvince_Id().toString()));
            data.setCities(ProvincesMapper.findCityById(data.getCity_Id().toString()));
            data.setAreas(ProvincesMapper.findAreaById(data.getArea_Id().toString()));
        }
        PageInfo pageInfo = new PageInfo(foodlist);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "toFood");
        return "index/food";
    }

    //主页
    @RequestMapping("/toIndex")
    public String toIndex(Model model, String key,
                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false, defaultValue = "8") Integer pageSize,
                          HttpSession httpSession, HttpServletRequest request) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<Goods>();
        List<Goods> goodslist = GoodsService.list(queryWrapper);//全部的景点
        for (Goods goods : goodslist) {
            if (goods.getCategory_id() != null) {
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        QueryWrapper<Goods> queryWrapperSalenums = new QueryWrapper<Goods>();
        queryWrapperSalenums.orderByDesc("salenums");//销量榜的排名
        List<Goods> goodslistSalenums = GoodsService.list(queryWrapperSalenums);//全部的景点
        for (Goods goods : goodslistSalenums) {
            if (goods.getCategory_id() != null) {
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        QueryWrapper<Goods> queryWrapperCollectnums = new QueryWrapper<Goods>();
        queryWrapperCollectnums.orderByDesc("collectnums");//收藏榜的排名
        List<Goods> goodslistcollectnums = GoodsService.list(queryWrapperCollectnums);//全部的景点
        for (Goods goods : goodslistcollectnums) {
            if (goods.getCategory_id() != null) {
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        model.addAttribute("goodslistcollectnums", goodslistcollectnums.size() > 3 ? goodslistcollectnums.subList(0, 3) : goodslistcollectnums);

        QueryWrapper<Goods> queryWrapperHot = new QueryWrapper<Goods>();
        queryWrapperHot.eq("ishot", 1);
        List<Goods> goodslisthot = GoodsService.list(queryWrapperHot);//热门的景点
        for (Goods goods : goodslisthot) {
            if (goods.getCategory_id() != null) {
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        model.addAttribute("goodslisthot", goodslisthot.size() > 3 ? goodslisthot.subList(0, 3) : goodslisthot);

        QueryWrapper<Goods> queryWrapperNew = new QueryWrapper<Goods>();
        queryWrapperNew.orderByDesc("createtime");
        List<Goods> goodslistnew = GoodsService.list(queryWrapperNew);//最新的景点
        for (Goods goods : goodslistnew) {
            if (goods.getCategory_id() != null) {
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        model.addAttribute("goodslistnew", goodslistnew.size() > 3 ? goodslistnew.subList(0, 3) : goodslistnew);

        QueryWrapper<Goods> queryWrapperSee = new QueryWrapper<Goods>();
        queryWrapperSee.orderByDesc("seenums");
        List<Goods> goodslistsee = GoodsService.list(queryWrapperSee);//热门的景点
        for (Goods goods : goodslistnew) {
            if (goods.getCategory_id() != null) {
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        model.addAttribute("goodslistsee", goodslistsee.size() > 3 ? goodslistsee.subList(0, 3) : goodslistsee);

        List<Category> categorylist = CategoryService.list();//全部的景点类型
        List<Banner> bannerlist = BannerService.list();//全部的轮播
        List<Board> boardlist = BoardService.list();//全部的旅游攻略
        model.addAttribute("bannerlist", bannerlist);
        model.addAttribute("categorylist", categorylist);
        model.addAttribute("boardlist", boardlist.size() > 4 ? boardlist.subList(0, 4) : boardlist);
        model.addAttribute("goodslist", goodslist);
        model.addAttribute("goodslistSalenums", goodslistSalenums.size() > 10 ? goodslistSalenums.subList(0, 10) : goodslistSalenums);
/*
        model.addAttribute("goodslist",goodslist.subList(0,2));
*/

        return "index/index";
    }

    @RequestMapping("/toHotel")
    public String toHotel(Model model, String names,
                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                          HttpSession httpSession, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Hotel> queryWrapper = new QueryWrapper<Hotel>();
        queryWrapper.like(StringUtils.hasText(names), "names", names);

        List<Hotel> hotellist = HotelService.list(queryWrapper);//全部的景点

        PageInfo pageInfo = new PageInfo(hotellist);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "toHotel");

        return "index/hotel";
    }

    @RequestMapping("/toGoods")
    public String toGoods(Model model, String names, String pricehigh, String pricelow, String collect,
                          String ishot, String category_id,
                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                          HttpSession httpSession, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<Goods>();
        queryWrapper.like(StringUtils.hasText(names), "names", names);
        queryWrapper.orderByDesc(pricehigh != null, "price");
        queryWrapper.orderByAsc(pricelow != null, "price");
        queryWrapper.orderByDesc(collect != null, "collectnums");
        queryWrapper.eq(ishot != null, "ishot", ishot);
        queryWrapper.eq(category_id != null, "category_id", category_id);

        List<Goods> goodslist = GoodsService.list(queryWrapper);//全部的景点
        for (Goods goods : goodslist) {
            if (goods.getCategory_id() != null) {
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        PageInfo pageInfo = new PageInfo(goodslist);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "toGoods");

        List<Category> categorylist = CategoryService.list();//全部的景点类型
        for (Category data : categorylist) {
            QueryWrapper<Goods> queryWrapperBussinessHasCategory = new QueryWrapper<Goods>();
            queryWrapperBussinessHasCategory.eq("category_id", data.getId());
            List<Goods> Goodslist = GoodsService.list(queryWrapperBussinessHasCategory);
            data.setNums(Goodslist.size());
        }
        model.addAttribute("categorylist", categorylist);
        return "index/goods";
    }


    @RequestMapping("/toGoodsDetail")
    public String toGoodsDetail(Model model, Integer id, HttpServletRequest request) {
        Goods goods = GoodsService.getById(id);
        goods.setSeenums(goods.getSeenums() + 1);
        GoodsService.updateById(goods);
        goods.setCategory(CategoryService.getById(goods.getCategory_id()));
        goods.setProvinces(ProvincesMapper.findProvinceById(goods.getProvince_Id().toString()));
        goods.setCities(ProvincesMapper.findCityById(goods.getCity_Id().toString()));
        goods.setAreas(ProvincesMapper.findAreaById(goods.getArea_Id().toString()));
        model.addAttribute("goods", goods);
        QueryWrapper<Comments> queryWrapperComments = new QueryWrapper<Comments>();
        queryWrapperComments.eq("goods_id", goods.getId());
        List<Comments> commentsList = CommentsService.list(queryWrapperComments);
        for (Comments data : commentsList) {
            data.setUser(UserService.getById(data.getUser_id()));
            data.setGoods(GoodsService.getById(data.getGoods_id()));
        }

        model.addAttribute("commentsList", commentsList);

        return "index/goods_detail";
    }

    @RequestMapping("/toBoard")
    public String toBoard(Model model, String title,
                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                          HttpSession httpSession, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Board> queryWrapper = new QueryWrapper<Board>();
        queryWrapper.like(StringUtils.hasText(title), "title", title);
        List<Board> boardlist = BoardService.list(queryWrapper);//全部的旅游攻略
        PageInfo pageInfo = new PageInfo(boardlist);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "toBoard");
        return "index/board";
    }


    @RequestMapping("/toBoard_detail")
    public String toBoard_detail(Model model, Integer id, HttpServletRequest request) {
        Board board = BoardService.getById(id);
        model.addAttribute("board", board);
        return "index/board_detail";
    }

    @RequestMapping("/toHotel_detail")
    public String toHotel_detail(Model model, Integer id, HttpServletRequest request) {
        Hotel hotel = HotelService.getById(id);
        model.addAttribute("hotel", hotel);
        return "index/hotel_detail";
    }


}
