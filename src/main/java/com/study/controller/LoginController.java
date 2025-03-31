package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.dao.AdminMapper;
import com.study.entity.Admin;

import com.study.entity.User;
import com.study.service.AdminService;


import com.study.service.UserService;
import com.study.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LoginController {

    @Autowired
    private UserService UserService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;

    @RequestMapping("/statics1")
    @ResponseBody
    public List<Map<String, Object>> statics(){
        List<Map<String, Object>> maps = adminMapper.statics1();
        return maps;
    }


    @RequestMapping("/statics2")
    @ResponseBody
    public List<Map<String, Object>> statics2(){
        List<Map<String, Object>> maps = adminMapper.statics2();
        return maps;
    }


    @RequestMapping("/statics3")
    @ResponseBody
    public List<Map<String, Object>> statics3(){
        List<Map<String, Object>> maps = adminMapper.statics3();
        return maps;
    }

    @RequestMapping("/statics4")
    @ResponseBody
    public List<Map<String, Object>> statics4(){
        List<Map<String, Object>> maps = adminMapper.statics4();
        return maps;
    }


    /**
     * 验证码方法
     */
    @RequestMapping("getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        //生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //验证码放入session
        session.setAttribute("code", code);
        //验证码存入图片
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(220, 60, os, code);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param type
     * @param httpSession
     * @param model
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public String loginUser(String username, String password, String type, String code, HttpSession httpSession, Model model) throws UnsupportedEncodingException {
        if (type != null && type.equals("1")) {
            QueryWrapper<Admin> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            wrapper.eq("password", password);
            Admin admin = adminService.getOne(wrapper);
            if (admin != null) {
                httpSession.setAttribute("username", admin.getUsername());
                httpSession.setAttribute("admin", admin);
                httpSession.setAttribute("type", type);
                model.addAttribute("type", type);
                            return "redirect:/BannerList";

            } else {
                model.addAttribute("status", "账号或者密码输入错误！");
                return "login";
            }
        } else if (type.equals("2")) {//用户
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("name", username);
            wrapper.eq("pwd", password);
            User user = UserService.getOne(wrapper);
            if (user != null) {
                httpSession.setAttribute("realname", user.getRealname());
                httpSession.setAttribute("user", user);
                httpSession.setAttribute("type", type);
                httpSession.setAttribute("photo", user.getPhoto());
                System.out.println(user.getPhoto());
                model.addAttribute("type", type);
                return "redirect:/toIndex";
            } else {
                model.addAttribute("status", "账号或者密码输入错误！");
                return "login";
            }
        } else {
            model.addAttribute("status", "账号或者密码输入错误！");
            return "login";
        }
    }


    /**
     * 登出
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/outLogin", method = RequestMethod.GET)
    public String outLogin(HttpSession httpSession) {
        httpSession.invalidate();
        return "login";
    }

    @RequestMapping(value = "/toConsole")
    public String toConsole() {
        return "home/console";
    }


    @RequestMapping(value = "/echart")
    public String echart() {
        return "son/echart";
    }

    @RequestMapping(value = "/toUpdateAdmin")
    public String toUpdateAdmin() {
        return "admin/admin";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }


    //修改管理员
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public String updateAdmin(String newPass, String password, Model model, HttpSession httpSession) {
        Admin admin = (Admin) (httpSession.getAttribute("admin"));
        String message = "no";
        if (admin != null && admin.getPassword().equals(password)) {
            admin.setPassword(newPass);
            adminService.updateById(admin);
            message = "yes";
        }
        return message;
    }


    @ResponseBody
    @RequestMapping("/upload")
    public Map upload(MultipartFile file, HttpServletRequest request) {

        String prefix = "";
        String dateStr = "";
        OutputStream out = null;
        InputStream fileInput = null;
        try {
            if (file != null) {
                String originalName = file.getOriginalFilename();
                prefix = originalName.substring(originalName.lastIndexOf(".") + 1);
                Date date = new Date();
                String uuid = UUID.randomUUID() + "";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = simpleDateFormat.format(date);
                String filepath = "D:\\upload\\" + dateStr + "\\" + uuid + "." + prefix;

                File files = new File(filepath);
                System.out.println(filepath);
                if (!files.getParentFile().exists()) {
                    files.getParentFile().mkdirs();
                }
                file.transferTo(files);
                Map<String, Object> map2 = new HashMap<>();
                Map<String, Object> map = new HashMap<>();
                map.put("code", 0);
                map.put("msg", "");
                map.put("data", map2);
                map2.put("src", "/images/" + dateStr + "/" + uuid + "." + prefix);
                return map;
            }

        } catch (Exception e) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fileInput != null) {
                    fileInput.close();
                }
            } catch (Exception e) {
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "");
        return map;

    }


    /**
     *  * 上传图片
     *  *
     *  * @param file
     *  * @param request
     *  * @param response
     *  * @return
     *  
     */
    @RequestMapping(value = "/uploadconimage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadconimage(HttpServletRequest request, @RequestParam MultipartFile file) {
        Map<String, Object> mv = new HashMap<String, Object>();
        Map<String, String> mvv = new HashMap<String, String>();
        try {
            String filepath = "D:\\upload\\";
            Calendar date = Calendar.getInstance(); //Calendar.getInstance()是获取一个Calendar对象并可以进行时间的计算，时区的指定
            String originalFile = file.getOriginalFilename(); //获得文件最初的路径
            String uuid = UUID.randomUUID().toString();    //UUID转化为String对象
            String newfilename = date.get(Calendar.YEAR) + "" + (date.get(Calendar.MONTH) + 1) + "" + date.get(Calendar.DATE) + uuid.replace("-", "") + originalFile;
            //得到完整路径名
            File newFile = new File(filepath + newfilename);
            /*文件不存在就创建*/
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            String filename = originalFile.substring(0, originalFile.indexOf("."));
            System.out.println(originalFile);
            System.out.println(filename);
            file.transferTo(newFile);
            System.out.println("newFile : " + newFile);
            String urlpat = "/images/" + newfilename;
            mvv.put("src", urlpat);
            mvv.put("title", newfilename);
            mv.put("code", 0);
            mv.put("msg", "上传成功");
            mv.put("data", mvv);
            return mv;
        } catch (Exception e) {
            e.printStackTrace();
            mv.put("success", 1);
            return mv;
        }
    }


    @ResponseBody
    @RequestMapping("/downLoad")
    public void downLoad(HttpServletRequest request, HttpServletResponse response) {
        File downLoadFileDir = new File("D:\\upload");
        String aFileName = null;// 要下载的文件名
        FileInputStream in = null;
        ServletOutputStream out = null;
        try {
            request.setCharacterEncoding("utf-8");
            aFileName = request.getParameter("resPath");
            aFileName = !aFileName.equals("") ? aFileName.substring(7, aFileName.length()) : aFileName;
            response.setHeader("Content-Type", "application/x-msdownload");// 设置下载文件使用的报头
            response.setHeader("Content-Disposition", "attachment;filename=" + toUTF8String(aFileName));
            in = new FileInputStream(downLoadFileDir + File.separator + aFileName);// 读入文件
            out = response.getOutputStream();// 得到输出流,用于向客户端输出二进制数据
            out.flush();
            int aRead = 0;
            byte b[] = new byte[1024];
            while ((aRead = in.read(b)) != -1 && in != null) {
                out.write(b, 0, aRead);
            }
            out.flush();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String toUTF8String(String str) {
        StringBuffer sb = new StringBuffer();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte b[];
                try {
                    b = Character.toString(c).getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    b = null;
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k &= 255;
                    }
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }

            }
        }
        return sb.toString();
    }


}
