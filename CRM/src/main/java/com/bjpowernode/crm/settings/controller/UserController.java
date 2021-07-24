package com.bjpowernode.crm.settings.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.exception.ActOrPwdException;
import com.bjpowernode.crm.settings.exception.ActTimeOutException;
import com.bjpowernode.crm.settings.exception.IpRestrictedException;
import com.bjpowernode.crm.settings.exception.LoginException;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService service;
    @ResponseBody
    @RequestMapping(value ="/login" ,method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String userLogin(User user, HttpServletRequest request) throws LoginException, JsonProcessingException {
        User user1 = service.login(user);
        String ips = "192.168.1.1,192.168.1.2,127.0.0.1";
        if (user1 == null) {
            throw new ActOrPwdException("用户名或者密码错误！");
        }else if ((user1.getExpireTime().compareTo(DateTimeUtil.getSysTime()) < 0)){
            throw new ActTimeOutException("用户账号已失效！");
        }else if (!ips.contains(user1.getAllowIps())){
            throw new IpRestrictedException("该Ip地址限制登录！");
        }else {
            HttpSession applicationContext = request.getSession();
            applicationContext.setAttribute("user",user1);
            Map<String,String> map = new HashMap<>();
            map.put("succeed","true");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);
        }
    }
}
