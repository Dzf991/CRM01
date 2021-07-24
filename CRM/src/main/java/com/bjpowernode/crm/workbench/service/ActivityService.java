package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.Map;

public interface ActivityService {
    String saveActivity(Activity activity);

    String getActivityList(PageVo<Activity> pageVo,Activity activity);

    String delActivity(String[] id);

    Map getActivityByAId(String id);

    String editActivityByAId(Activity activity);

    Activity getActivityDetails(String Id);

    String getActivityRemark(String id);

    String addActivityRemark(ActivityRemark activityRemark);

    String editActivityRemark(ActivityRemark activityRemark);

    String delActivityRemark(String id);
}
