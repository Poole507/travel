package com.study.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

    /**
     * 拦截器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getServletPath();
        logger.info("request请求地址path[{}] uri[{}]", request.getServletPath(),request.getRequestURI());
        Object type = request.getSession().getAttribute("type");//用户登录以后的标志。
        //如果是用户已经登录，或者请求部分功能如注册登录、首页查看等，均放行
        if (type != null || url.contains("Login") || url.contains("login") || url.contains("register")
                || url.contains("getImage") || url.contains("registerUser") || url.contains("addUser")
                || url.contains("toIndex") || url.contains("toGoods")   || url.contains("toBoard")
                || url.contains("toBussiness")
                || url.contains("outLogin")    || url.contains("addBussiness")
                || url.contains("toFood")
                || url.contains("toHotel")

        ) {
            return true;
        }
        //处理的是用户未登录的情况下，对于Ajax请求的拦截
            if (type == null&&request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with")
                    .equalsIgnoreCase("XMLHttpRequest")) {
                //如果是ajax请求响应头会有x-requested-with
                System.out.println("ajax请求被拦截");
                response.setHeader("Access-Control-Expose-Headers", "REDIRECT,CONTEXTPATH");
                //告诉ajax我是重定向
                response.setHeader("REDIRECT", "REDIRECT");
                //告诉ajax我重定向的路径
                response.setHeader("CONTEXTPATH", "outLogin");
                response.getWriter().write(1);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        //其余的一切请求均被拦截
        else{
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("<script type='text/javascript'>alert('请先登录！');"
                    + "window.parent.location.href = 'outLogin'; </script>");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {}


}
