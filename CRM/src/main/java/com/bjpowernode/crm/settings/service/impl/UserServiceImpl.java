package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.exception.ActOrPwdException;
import com.bjpowernode.crm.settings.exception.ActTimeOutException;
import com.bjpowernode.crm.settings.exception.IpRestrictedException;
import com.bjpowernode.crm.settings.exception.LoginException;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.workbench.domain.Clue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User login(User user) throws LoginException {
       return userDao.login(user);

    }

    @Override
    public List<User> findUser() {
        return userDao.findUser();
    }

}
