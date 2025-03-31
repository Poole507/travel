package com.study.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.entity.Board;
import com.study.entity.Board;
import com.study.entity.User;
import com.study.service.BoardService;
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
public class BoardController {


    @Autowired
    private BoardService BoardService;

    //删除分类
    @RequestMapping("/deleteBoard")
    @ResponseBody
    public String deleteBoard(Integer id, Model model, HttpSession httpSession){
        String message = "no";
        BoardService.removeById(id);
        message = "yes";
        return message;
    }

    //新增分类页面
    @RequestMapping("/toAddBoard")
    public String toAddBoards( HttpServletRequest request){
        return "board/add";
    }

    //新增分类
    @RequestMapping(value = "/addBoard", method = RequestMethod.POST)
    public String addBoard(Board data, HttpSession httpSession, Model model) throws UnsupportedEncodingException {
        data.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        BoardService.save(data);
        return "redirect:/BoardList";
    }

    //修改分类
    @RequestMapping(value = "/updateBoard", method = RequestMethod.POST)
    public String updateBoard(Board data) throws UnsupportedEncodingException {
        BoardService.updateById(data);
        return "redirect:/BoardList";
    }

    //修改页面
    @RequestMapping("/toUpdateBoard")
    public String toUpdateBoard(Integer id, Model model){
        Board data =BoardService.getById(id);
        model.addAttribute("data",data);
        return "board/update";
    }

    //查询分类
    @RequestMapping("/BoardList")
    public String BoardList(Model model){
        List<Board> list = BoardService.list();
        model.addAttribute("list",list);
        return "board/list";
    }



}
