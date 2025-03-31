package com.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:D://upload//");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        访问首页跳转
        registry.addViewController("/").setViewName("redirect:/toIndex");
        registry.addViewController("login.html").setViewName("login");
//        跳转登录
        registry.addViewController("toLogin").setViewName("login");
//        跳转找回密码
        registry.addViewController("tofindpwd").setViewName("findpwd");
//        跳转首页
       /* registry.addViewController("menu").setViewName("home/menu");
        registry.addViewController("menu.html").setViewName("home/menu");*/
        registry.addViewController("echart.html").setViewName("son/echart");
    }

//    拦截器配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        设置公开的资源
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login.html","/","/register.html", "/register/**", "/login/css/**", "/login/img/**","/static/**","/assets/**","/assets/js/**",
                        "/assets/css/**","/assets/fonts/**","/assets/imges/**","/assets/libs/**","/layui/**",
                        "/ajaxUsername","/loginUser","/toReg","/toReg2","/toLogin","/registerUser","/registerUser2",

                        "/toIndex",
                        "/upload","/images/**",
                        "/ajaxMailCode","/tofindpwd","/findPwd","/findpwd.html","/setpwd.html",
                        "/setPwd","error/404.html",

                        "/static/css/**","/static/js/**"
                );
    }



}
