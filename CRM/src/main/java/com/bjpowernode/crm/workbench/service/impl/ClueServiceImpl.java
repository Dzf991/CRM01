package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;
    @Override
    public String addClue(Clue clue) {
        int result = clueDao.addClue(clue);
        if (result == 1){
            return PrintJson.printJsonFlag(true);
        }else {
            return PrintJson.printJsonFlag(false);
        }
    }

    @Override
    public Clue getDetail(String id) {

        return clueDao.getDetail(id);

    }

    @Override
    public String getClueRelation(String clueId) {
       List<Activity> clueActivityRelationList = clueActivityRelationDao.getClueRelation(clueId);

        return PrintJson.printJsonObj(clueActivityRelationList);
    }

    @Override
    public String delRelation(String clueId) {
        int result = clueActivityRelationDao.delRelation(clueId);
        if (result == 1){
            return PrintJson.printJsonFlag(true);
        }else {
            return PrintJson.printJsonFlag(false);
        }

    }

    @Override
    public String getActivityList(String name,String clueId) {
       List<Activity> activityList = clueDao.getActivityList(name,clueId);
       return PrintJson.printJsonObj(activityList);
    }

    @Override
    public String createRelation(List<ClueActivityRelation> clueActivityRelationList) {
        int result = clueActivityRelationDao.createRelation(clueActivityRelationList);
        if (result >= 1){
            return PrintJson.printJsonFlag(true);
        }else {
            return PrintJson.printJsonFlag(false);
        }
    }

    @Override
    public String searchActivity(String name) {
        List<Activity> activityList = clueDao.searchActivity(name);
        return PrintJson.printJsonObj(activityList);
    }
    @Transactional
    @Override
    public String doConvert(String clueId, Tran tran, String flag, String createBy ) {
        Clue clue = clueDao.getClueDetail(clueId);
        Contacts contacts = new Contacts();
        String createTime = DateTimeUtil.getSysTime();

        Customer customer = customerDao.getCustomerByName(clue.getCompany());
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setCreateTime(createTime);
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(clue.getCompany());
            customer.setDescription(clue.getDescription());
            customer.setCreateBy(createBy);
            customer.setContactSummary(clue.getContactSummary());
            customerDao.addCustomer(customer);
        }


        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setCreateBy(createBy);
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setSource(clue.getSource());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setCreateTime(createTime);
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        contactsDao.addContacsDao(contacts);


        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationDao.getclueActivityRelationById(clueId);
        ContactsActivityRelation contactsActivityRelation = null;
        List<ContactsActivityRelation> contactsActivityRelations = new ArrayList<>();
        for (ClueActivityRelation activityRelation: clueActivityRelations) {
            contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityRelation.getActivityId());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelations.add(contactsActivityRelation);
        }
        contactsActivityRelationDao.addContactsActivityRelation(contactsActivityRelations);

        if (tran != null){
            tranDao.addTran(tran);
            tranHistoryDao.addtranHistory(tran);
        }

        clueActivityRelationDao.delRelation(clueId);
        clueDao.delClue(clueId);
        int result = clueRemarkDao.delclueRemark(clueId);
        if (result == 1){
            return "success";
        }else {
            return "failed";
        }
    }

    @Override
    public String getClueList(Clue clue, PageVo<Clue> pageVo) {
        String total = String.valueOf(clueDao.getClueCount(clue,pageVo));
        pageVo.setTotal(total);

        List<Clue> clueList = clueDao.getClueList(clue,pageVo);

        pageVo.setPageList(clueList);

        return PrintJson.printJsonObj(pageVo);
    }
}
