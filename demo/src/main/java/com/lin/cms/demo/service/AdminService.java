package com.lin.cms.demo.service;

import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.NotFound;
import com.lin.cms.exception.Parameter;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.bo.GroupWithAuthsBO;


public interface AdminService {
    PageResult getUsers(Integer groupId, Integer count, Integer page);

    void changeUserPassword(Integer id, ResetPasswordDTO validator) throws NotFound;

    void deleteUser(Integer id) throws NotFound;

    void updateUserInfo(Integer id, UpdateUserInfoDTO validator) throws NotFound, Parameter;

    PageResult getGroups(Integer page, Integer count);

    GroupWithAuthsBO getGroup(Integer id);

    void createGroup(NewGroupDTO validator) throws Forbidden;

    void updateGroup(Integer id, UpdateGroupDTO validator) throws NotFound;

    void deleteGroup(Integer id) throws NotFound, Forbidden;

    void dispatchAuth(DispatchAuthDTO validator) throws NotFound, Forbidden;

    void dispatchAuths(DispatchAuthsDTO validator) throws NotFound;

    void removeAuths(RemoveAuthsDTO validator) throws NotFound;
}
