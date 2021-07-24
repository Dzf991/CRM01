package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueDao {
    int addClue(Clue clue);

    Clue getDetail(String id);

    List<Activity> getActivityList(@Param(value = "name") String name, @Param(value = "clueId") String clueId);

    List<Activity> searchActivity(String name);

    Clue getClueDetail(String clueId);

    int delClue(String clueId);

    int getClueCount(@Param("clue") Clue clue,@Param("pageVo") PageVo<Clue> pageVo);

    List<Clue> getClueList(@Param("clue") Clue clue,@Param("pageVo") PageVo<Clue> pageVo);
}
