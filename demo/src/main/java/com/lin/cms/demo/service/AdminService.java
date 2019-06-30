package com.lin.cms.demo.service;

import com.lin.cms.core.exception.Forbidden;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.validators.admin.*;
import com.lin.cms.demo.BO.GroupWithAuthsBO;


public interface AdminService {
    PageResult getUsers(Integer groupId, Integer count, Integer page);

    void changeUserPassword(Integer id, ResetPasswordValidator validator) throws NotFound;

    void deleteUser(Integer id) throws NotFound;

    void updateUserInfo(Integer id, UpdateUserInfoValidator validator) throws NotFound, Parameter;

    PageResult getGroups(Integer page, Integer count);

    GroupWithAuthsBO getGroup(Integer id);

    void createGroup(NewGroupValidator validator) throws Forbidden;

    void updateGroup(Integer id, UpdateGroupValidator validator) throws NotFound;

    void deleteGroup(Integer id) throws NotFound, Forbidden;

    void dispatchAuth(DispatchAuthValidator validator) throws NotFound, Forbidden;

    void dispatchAuths(DispatchAuthsValidator validator) throws NotFound;

    void removeAuths(RemoveAuthsValidator validator) throws NotFound;
}
