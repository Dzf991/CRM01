package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityDao {
    int saveActivity(Activity activity);

    int getTotal(@Param(value = "pageVo") PageVo<Activity> pageVo,@Param(value = "activity") Activity activity);

    List<Activity> getActivityList(@Param(value = "pageVo") PageVo<Activity> pageVo,@Param(value = "activity") Activity activity);

    int delActivity(String[] id);

    Activity getActivityById(String id);

    int editActivityByAId(Activity activity);

    Activity getActivityDetails(String id);
}
