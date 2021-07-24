package com.bjpowernode.crm.settings.handler;

import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        System.out.println(path);


        if (path.indexOf("index.jsp") >= 1 || path.indexOf("login") >= 1){
            return true;
        }
//        HttpSession applicationContext = request.getSession(false);
//        User user = (User) applicationContext.getAttribute("user");
        HttpSession applicationContext = request.getSession(false);

        if (applicationContext == null) {
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
