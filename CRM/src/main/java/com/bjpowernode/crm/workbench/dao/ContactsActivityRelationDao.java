package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContactsActivityRelationDao {


    int addContactsActivityRelation(@Param("contactsActivityRelations") List<ContactsActivityRelation> contactsActivityRelations);
}
