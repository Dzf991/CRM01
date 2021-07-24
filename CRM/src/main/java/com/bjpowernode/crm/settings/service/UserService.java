package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.exception.ActOrPwdException;
import com.bjpowernode.crm.settings.exception.LoginException;
import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;

public interface UserService {
    User login(User user) throws LoginException, LoginException;
    List<User> findUser();


}
