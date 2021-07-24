package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityDao activityDao;
    @Autowired
    ActivityRemarkDao activityRemarkDao;
    @Autowired
    UserDao userDao;
    @Override
    public String saveActivity(Activity activity) {
        int result = activityDao.saveActivity(activity);
        Map<String, Boolean> map = new HashMap<>();

        if (result == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return PrintJson.printJsonObj(map);

    }

    @Override
    public String getActivityList(PageVo<Activity> pageVo, Activity activity) {
        String total = String.valueOf(activityDao.getTotal(pageVo, activity));
        pageVo.setTotal(total);
        List<Activity> activityList = activityDao.getActivityList(pageVo,activity);
        pageVo.setPageList(activityList);
        return PrintJson.printJsonObj(pageVo);
    }

    @Override
    public String delActivity(String[] id) {
        int result = activityDao.delActivity(id);
        if (result >= 1){
            return PrintJson.printJsonFlag(true);
        }else {
            return PrintJson.printJsonFlag(false);
        }
    }

    @Override
    public Map<String,Object> getActivityByAId(String id) {
        List<User> userList = userDao.findUser();

        Activity activity = activityDao.getActivityById(id);

        Map<String,Object> map = new HashMap<>();
        map.put("activity",activity);
        map.put("users",userList);
        return map;
    }

    @Override
    public String editActivityByAId(Activity activity) {

        int result = activityDao.editActivityByAId(activity);
        if (result >= 1){
            return PrintJson.printJsonFlag(true);
        }else {
            return PrintJson.printJsonFlag(false);
        }
    }

    @Override
    public Activity getActivityDetails(String id) {
        return activityDao.getActivityDetails(id);
    }

    @Override
    public String getActivityRemark(String id) {
        List<ActivityRemark> activityRemarks = activityRemarkDao.getActivityRemarks(id);
        Map<String,Object> map = new HashMap<>();
        if (activityRemarks != null) {
            map.put("activityRemarks",activityRemarks);
            map.put("success",true);
            return PrintJson.printJsonObj(map);
        }else {
            map.put("success",false);
            return PrintJson.printJsonObj(map);
        }

    }

    @Override
    public String addActivityRemark(ActivityRemark activityRemark) {
        int result = activityRemarkDao.addActivityRemark(activityRemark);
        if (result == 1){
            return PrintJson.printJsonFlag(true);
        }else {
            return PrintJson.printJsonFlag(false);
        }
    }

    @Override
    public String editActivityRemark(ActivityRemark activityRemark) {
        int result = activityRemarkDao.editActivityRemark(activityRemark);
        if (result == 1){
            return PrintJson.printJsonFlag(true);
        }else {
            return PrintJson.printJsonFlag(false);
        }
    }

    @Override
    public String delActivityRemark(String id) {
        int result = activityRemarkDao.delActivityRemark(id);
        if (result == 1){
            return PrintJson.printJsonFlag(true);
        }else {
            return PrintJson.printJsonFlag(false);
        }
    }
}
