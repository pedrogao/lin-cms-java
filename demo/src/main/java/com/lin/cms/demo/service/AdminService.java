package com.lin.cms.demo.service;

import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.bo.GroupAuthsBO;

import java.util.List;


public interface AdminService {
    PageResult getUsers(Long groupId, Long count, Long page);

    void changeUserPassword(Long id, ResetPasswordDTO validator);

    void deleteUser(Long id);

    void updateUserInfo(Long id, UpdateUserInfoDTO validator);

    PageResult getGroups(Long page, Long count);

    GroupAuthsBO getGroup(Long id);

    void createGroup(NewGroupDTO validator);

    void updateGroup(Long id, UpdateGroupDTO validator);

    void deleteGroup(Long id);

    void dispatchAuth(DispatchAuthDTO validator);

    void dispatchAuths(DispatchAuthsDTO validator);

    void removeAuths(RemoveAuthsDTO validator);

    List<GroupDO> getAllGroups();
}
