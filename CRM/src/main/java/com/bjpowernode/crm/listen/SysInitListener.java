package com.bjpowernode.crm.listen;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.settings.service.impl.DicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        DicService dicService = (DicService) webApplicationContext.getBean("dicServiceImpl");
        ServletContext application = event.getServletContext();
        Map<String, List<DicValue>> stringListMap = dicService.dicTypeValue();

        Set<String> keys = stringListMap.keySet();
        for (String key :keys) {
            application.setAttribute(key+"s",stringListMap.get(key));
        }
    }
}
