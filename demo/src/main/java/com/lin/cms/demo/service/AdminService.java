package com.lin.cms.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.model.PermissionDO;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.bo.GroupPermissionsBO;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.model.GroupDO;

import java.util.List;
import java.util.Map;

public interface AdminService {

    IPage<UserDO> getUserPageByGroupId(Long groupId, Long count, Long page);

    boolean changeUserPassword(Long id, ResetPasswordDTO dto);

    boolean deleteUser(Long id);

    boolean updateUserInfo(Long id, UpdateUserInfoDTO dto);

    IPage<GroupDO> getGroupPage(Long page, Long count);

    GroupPermissionsBO getGroup(Long id);

    boolean createGroup(NewGroupDTO dto);

    boolean updateGroup(Long id, UpdateGroupDTO dto);

    boolean deleteGroup(Long id);

    boolean dispatchPermission(DispatchPermissionDTO dto);

    boolean dispatchPermissions(DispatchPermissionsDTO dto);

    boolean removePermissions(RemovePermissionsDTO dto);

    List<GroupDO> getAllGroups();

    List<PermissionDO> getAllPermissions();

    Map<String, List<PermissionDO>> getAllStructualPermissions();
}
