package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.Tran;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ClueService {

    String addClue(Clue clue);

    Clue getDetail(String id);

    String getClueRelation(String clueId);

    String delRelation(String clueId);

    String getActivityList(String name,String clueId);

    String createRelation(List<ClueActivityRelation> clueActivityRelationList);

    String searchActivity(String name);


    String doConvert(String clueId, Tran tran, String flag, String createBy);

    String getClueList(Clue clue, PageVo<Clue> pageVo);
}
