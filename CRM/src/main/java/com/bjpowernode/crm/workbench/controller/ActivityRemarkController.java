package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ActivityRemarkController {
    @Autowired
    ActivityService activityService;
    @ResponseBody
    @RequestMapping(value = "/workbench/activityRemark/getActivityRemark")
    public String getActivityRemark(String id){
       return activityService.getActivityRemark(id);
    }


    @ResponseBody
    @RequestMapping(value = "/workbench/activityRemark/addActivityRemark")
    public String addActivityRemark(ActivityRemark activityRemark){
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setId(UUIDUtil.getUUID());
        return activityService.addActivityRemark(activityRemark);
    }


    @ResponseBody
    @RequestMapping(value = "/workbench/activityRemark/editActivityRemark")
    public String editActivityRemark(ActivityRemark activityRemark){
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        return activityService.editActivityRemark(activityRemark);
    }

    @ResponseBody
    @RequestMapping(value = "/workbench/activityRemark/delActivityRemark")
    public String delActivityRemark(String id){
        return activityService.delActivityRemark(id);
    }
}
