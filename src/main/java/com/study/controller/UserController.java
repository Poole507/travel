package com.study.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.UserMapper;
import com.study.entity.Admin;
import com.study.entity.User;
import com.study.entity.User;
import com.study.service.UserService;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class UserController {
    @Autowired
     UserMapper UserMapper;

    @Autowired
    private UserService UserService;

    //删除用户
    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(Integer id, Model model, HttpSession httpSession){
        String message = "no";
        UserService.removeById(id);
        message = "yes";
        return message;
    }

    //新增用户页面
    @RequestMapping("/toAddUser")
    public String toAddUsers( HttpServletRequest request){
        return "user/add";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(User data, HttpSession httpSession, Model model) throws UnsupportedEncodingException {
        data.setCtime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        QueryWrapper<User> queryWrapper = new  QueryWrapper<>();
        queryWrapper.eq("name",data.getName());
        User userinfo = UserService.getOne(queryWrapper);
            if(userinfo == null){
                UserService.save(data);
                return("redirect:/UserList");
            }else{
                model.addAttribute("status", "用户名重复！");
                return("user/add");
            }
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(User data, HttpSession httpSession, Model model) throws UnsupportedEncodingException {
        data.setCtime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        QueryWrapper<User> queryWrapper = new  QueryWrapper<>();
        queryWrapper.eq("name",data.getName());
        User user = UserService.getOne(queryWrapper);
        if(user == null){
            UserService.save(data);
            return "login";
        }else{
            model.addAttribute("status", "用户名重复！");
            return "register";
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(HttpSession httpSession, Model model, User data) throws UnsupportedEncodingException {
        User User = (User)httpSession.getAttribute("user");//当前登录人
        Admin Admin = (Admin)httpSession.getAttribute("admin");
        if(User!=null){
            QueryWrapper<User> queryWrapper = new  QueryWrapper<>();
            queryWrapper.eq("name",data.getName());
            User userinfo = UserService.getOne(queryWrapper);
            if(!data.getName().equals(User.getName())){
                if(userinfo == null){
                    UserService.updateById(data);
                    httpSession.setAttribute("realname", UserService.getById(data.getId()).getRealname());
                    httpSession.setAttribute("user", UserService.getById(data.getId()));
                    return("redirect:/UserList");
                }else{
                    model.addAttribute("usertype",2);
                    model.addAttribute("status", "用户名重复！");
                    model.addAttribute("data", UserService.getById(data.getId()));
                    return("user/update");
                }
            }//账号不变的情况下，直接做修改
            else{
                UserService.updateById(data);
                httpSession.setAttribute("realname", UserService.getById(data.getId()).getRealname());
                httpSession.setAttribute("user", UserService.getById(data.getId()));
                return("redirect:/UserList");
            }
        }
        if(Admin!=null){
            UserService.updateById(data);
            return("redirect:/UserList");
        }else {
            UserService.updateById(data);
            return("redirect:/UserList");
        }
    }

    //修改页面
    @RequestMapping("/toUpdateUser")
    public String toUpdateUser(HttpSession httpSession,Integer id, Model model){
        User User = (User)httpSession.getAttribute("user");
        Admin Admin = (Admin)httpSession.getAttribute("admin");
        if(Admin!=null){
            model.addAttribute("usertype",1);
        }
        if(User!=null){
            model.addAttribute("usertype",2);
        }
        User data =UserService.getById(id);
        model.addAttribute("data",data);
        return "user/update";
    }


    //查询用户
    @RequestMapping("/UserList")
    public String UserList(Model model,HttpSession httpSession, HttpServletRequest request){
        User User = (User)httpSession.getAttribute("user");
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        if(User!=null){
            queryWrapper.eq("id",User.getId());
        }
        List<User> list = UserService.list(queryWrapper);
        model.addAttribute("list",list);
        return "user/list";
    }

}
