package com.bjpowernode.crm.workbench.dao;


import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueActivityRelationDao {

    List<Activity> getClueRelation(String clueId);


    int delRelation(String clueId);

    int createRelation(@Param("clueActivityRelationList") List<ClueActivityRelation> clueActivityRelationList);

    List<ClueActivityRelation> getclueActivityRelationById(String clueId);
}
