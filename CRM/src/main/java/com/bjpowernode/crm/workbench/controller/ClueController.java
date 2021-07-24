package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClueController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClueService clueService;

    @ResponseBody
    @RequestMapping(value = "/workbench/clue/getOwners")
    public String getOwners(){
        List<User> userList = userService.findUser();
        return PrintJson.printJsonObj(userList);
    }

    @ResponseBody
    @RequestMapping(value = "/workbench/clue/addClue")
    public String addClue(Clue clue){
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        return clueService.addClue(clue);
    }

    @ResponseBody
    @RequestMapping(value = "/workbench/clue/getDetail")
    public ModelAndView getDetail(String id){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("detail",clueService.getDetail(id));

        modelAndView.setViewName("/workbench/clue/detail.jsp");

        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "/workbench/clue/getClueRelation",method = RequestMethod.POST)
    public String getClueRelation(String clueId){

    return clueService.getClueRelation(clueId);

    }
    @ResponseBody
    @RequestMapping(value = "/workbench/clue/delRelation")
    public String unbound(HttpServletRequest request){
        String id = request.getParameter("id");
        return clueService.delRelation(id);

    }

    @ResponseBody
    @RequestMapping(value = "/workbench/clue/getActivity")
    public String getActivity(String name,String clueId){
      return  clueService.getActivityList(name,clueId);
    }

    @ResponseBody
    @RequestMapping(value = "/workbench/clue/createRelation")
    public String createRelation(String clueId, String[] activityId){
        List<ClueActivityRelation> clueActivityRelationList = new ArrayList<>();
        for (String id: activityId) {
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setActivityId(id);
            clueActivityRelation.setClueId(clueId);
            clueActivityRelationList.add(clueActivityRelation);
        }
         return clueService.createRelation(clueActivityRelationList);
    }

    @ResponseBody
    @RequestMapping(value = "/workbench/clue/searchActivity")
    public String searchActivity(String name){
        return  clueService.searchActivity(name);
    }
    @ResponseBody
    @RequestMapping(value = "/workbench/clue/convert")
    public ModelAndView convert(String clueId, String flag, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Tran tran = null;
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String createBy = user.getName();
        if ("true".equals(flag)){
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            tran = new Tran();
            tran.setActivityId(activityId);
            tran.setStage(stage);
            tran.setName(name);
            tran.setMoney(money);
            tran.setExpectedDate(expectedDate);
            tran.setCreateBy(createBy);

        }
        String s = clueService.doConvert(clueId, tran, flag, createBy);
        if ("success".equals(s)){
            modelAndView.setViewName("workbench/transaction/index.html");
        }
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/workbench/clue/getClueList")
    public String getClueList(Clue clue, Integer pageSize, Integer pageNo){
        Integer skipCount = (pageNo - 1) * pageSize;
        PageVo<Clue> pageVo = new PageVo<>();
        pageVo.setSkipCount(skipCount);
        pageVo.setPageSize(pageSize);
        return clueService.getClueList(clue,pageVo);
    }
}
