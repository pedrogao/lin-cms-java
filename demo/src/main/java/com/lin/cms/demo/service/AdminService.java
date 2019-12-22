package com.lin.cms.demo.service;

import com.lin.cms.demo.model.PermissionDO;
import com.lin.cms.demo.vo.PageResultVO;
import com.lin.cms.demo.bo.GroupPermissionsBO;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.model.GroupDO;

import java.util.List;
import java.util.Map;

public interface AdminService {

    PageResultVO getUserPageByGroupId(Long groupId, Long count, Long page);

    boolean changeUserPassword(Long id, ResetPasswordDTO dto);

    boolean deleteUser(Long id);

    boolean updateUserInfo(Long id, UpdateUserInfoDTO dto);

    PageResultVO getGroupPage(Long page, Long count);

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
