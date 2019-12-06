package com.lin.cms.demo.v2.service;

import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.bo.GroupPermissionsBO;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.v2.model.GroupDO;

import java.util.List;

public interface AdminService {

    PageResult getUsers(Long groupId, Long count, Long page);

    boolean changeUserPassword(Long id, ResetPasswordDTO validator);

    boolean deleteUser(Long id);

    boolean updateUserInfo(Long id, UpdateUserInfoDTO validator);

    PageResult getGroups(Long page, Long count);

    GroupPermissionsBO getGroup(Long id);

    boolean createGroup(NewGroupDTO validator);

    boolean updateGroup(Long id, UpdateGroupDTO validator);

    boolean deleteGroup(Long id);

    boolean dispatchAuth(DispatchAuthDTO validator);

    boolean dispatchAuths(DispatchAuthsDTO validator);

    boolean removeAuths(RemoveAuthsDTO validator);

    List<GroupDO> getAllGroups();
}
