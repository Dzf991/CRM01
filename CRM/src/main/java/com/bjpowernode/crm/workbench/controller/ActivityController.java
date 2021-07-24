package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ActivityController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @ResponseBody
    @RequestMapping(value = "/workbench/activity/findUser")
    public String findUser(){
        String jsonObj = "";
        List<User> userList = userService.findUser();
        if (userList != null) {
            jsonObj= PrintJson.printJsonObj(userList);
        }

       return jsonObj;
    }
    @ResponseBody
    @RequestMapping(value = "/workbench/activity/saveActivity",method = RequestMethod.POST)
    public String saveActivity(Activity activity){
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());

        return activityService.saveActivity(activity);
    }

    @ResponseBody
    @RequestMapping(value = "/workbench/activity/pageList")
    public String serch(Activity activity,Integer pageNo, Integer pageSize){

        Integer skipCount = (pageNo - 1) * pageSize;
        PageVo<Activity> pageVo = new PageVo<>();
        pageVo.setSkipCount(skipCount);
        pageVo.setPageSize(pageSize);
        return activityService.getActivityList(pageVo,activity);

    }

    @ResponseBody
    @RequestMapping(value = "/workbench/activity/delActivity",method = RequestMethod.POST)
    public String delActivity(String[] id){

        return  activityService.delActivity(id);
    }

    @ResponseBody
    @RequestMapping(value = "/workbench/activity/getActivityByAId",method = RequestMethod.POST)
    public String getActivityByAId(String id){
        return PrintJson.printJsonObj(activityService.getActivityByAId(id));
    }

    @ResponseBody
    @RequestMapping(value = "/workbench/activity/editActivity",method = RequestMethod.POST)
    public String editActivity(Activity activity){
        activity.setEditTime(DateTimeUtil.getSysTime());
       return activityService.editActivityByAId(activity);
    }


    @ResponseBody
    @RequestMapping(value = "/workbench/activity/getActivityDetails")
    public ModelAndView getActivityDetails(String id){
        ModelAndView mv = new ModelAndView();
        Activity activityDetails = activityService.getActivityDetails(id);

        mv.addObject("activityDetails",activityDetails);

        mv.setViewName("/workbench/activity/detail.jsp");

        return mv;

    }
}
