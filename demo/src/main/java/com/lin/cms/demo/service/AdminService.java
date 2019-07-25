package com.lin.cms.demo.service;

import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.NotFound;
import com.lin.cms.exception.Parameter;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.bo.GroupWithAuthsBO;

import java.util.List;


public interface AdminService {
    PageResult getUsers(Long groupId, Long count, Long page);

    void changeUserPassword(Long id, ResetPasswordDTO validator) throws NotFound;

    void deleteUser(Long id) throws NotFound;

    void updateUserInfo(Long id, UpdateUserInfoDTO validator) throws NotFound, Parameter;

    PageResult getGroups(Long page, Long count);

    GroupWithAuthsBO getGroup(Long id);

    void createGroup(NewGroupDTO validator) throws Forbidden;

    void updateGroup(Long id, UpdateGroupDTO validator) throws NotFound;

    void deleteGroup(Long id) throws NotFound, Forbidden;

    void dispatchAuth(DispatchAuthDTO validator) throws NotFound, Forbidden;

    void dispatchAuths(DispatchAuthsDTO validator) throws NotFound;

    void removeAuths(RemoveAuthsDTO validator) throws NotFound;

    List<GroupDO> getAllGroups();
}
