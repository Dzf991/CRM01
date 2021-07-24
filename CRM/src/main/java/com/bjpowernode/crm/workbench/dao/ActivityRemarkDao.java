package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    List<ActivityRemark> getActivityRemarks(String Aid);

    int addActivityRemark(ActivityRemark activityRemark);

    int editActivityRemark(ActivityRemark activityRemark);

    int delActivityRemark(String id);


}
