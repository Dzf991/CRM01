package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;
    @Override
    public Map<String,List<DicValue>> dicTypeValue() {

        Map<String,List<DicValue>> map = new HashMap<>();
        List<DicType> dicValueList = dicTypeDao.selectDicTypes();

        for (DicType dicType : dicValueList) {
            List<DicValue> dicValues = dicValueDao.selectDicValues(dicType.getCode());
            map.put(dicType.getCode(),dicValues);
        }

        return map;
    }
}
