package com.lin.cms.demo.service;

import com.lin.cms.demo.vo.PageResult;
import com.lin.cms.demo.bo.GroupPermissionsBO;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.model.GroupDO;

import java.util.List;

public interface AdminService {

    PageResult getUserPageByGroupId(Long groupId, Long count, Long page);

    boolean changeUserPassword(Long id, ResetPasswordDTO dto);

    boolean deleteUser(Long id);

    boolean updateUserInfo(Long id, UpdateUserInfoDTO dto);

    PageResult getGroupPage(Long page, Long count);

    GroupPermissionsBO getGroup(Long id);

    boolean createGroup(NewGroupDTO dto);

    boolean updateGroup(Long id, UpdateGroupDTO dto);

    boolean deleteGroup(Long id);

    boolean dispatchPermission(DispatchPermissionDTO dto);

    boolean dispatchPermissions(DispatchPermissionsDTO dto);

    boolean removePermissions(RemovePermissionsDTO dto);

    List<GroupDO> getAllGroups();
}
