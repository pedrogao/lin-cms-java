package com.lin.cms.demo.v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.bo.GroupPermissionsBO;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.v2.model.GroupDO;
import com.lin.cms.demo.v2.model.UserDO;
import com.lin.cms.demo.v2.model.UserIdentityDO;
import com.lin.cms.demo.v2.service.AdminService;
import com.lin.cms.demo.v2.service.GroupService;
import com.lin.cms.demo.v2.service.UserIdentityService;
import com.lin.cms.demo.v2.service.UserService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("adminServiceImpl-v2")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserIdentityService userIdentityService;

    @Autowired
    private GroupService groupService;

    @Override
    public PageResult getUsers(Long groupId, Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<UserDO> iPage = userService.findUsersByPage(pager, groupId);
        return PageResult.genPageResult(iPage.getTotal(), iPage.getRecords(), page, count);
    }

    @Override
    public boolean changeUserPassword(Long id, ResetPasswordDTO dto) {
        throwUserNotExistById(id);
        return userIdentityService.changePassword(id, dto.getNewPassword());
    }

    @Transactional
    @Override
    public boolean deleteUser(Long id) {
        throwUserNotExistById(id);
        boolean userRemoved = userService.removeById(id);
        QueryWrapper<UserIdentityDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserIdentityDO::getUserId, id);
        return userRemoved && userIdentityService.remove(wrapper);
    }

    @Override
    public boolean updateUserInfo(Long id, UpdateUserInfoDTO validator) {
        return false;
    }

    @Override
    public PageResult getGroups(Long page, Long count) {
        Page pager = new Page(page, count);
        IPage<GroupDO> iPage = groupService.findGroupsByPage(pager);
        return PageResult.genPageResult(iPage.getTotal(), iPage.getRecords(), page, count);
    }

    @Override
    public GroupPermissionsBO getGroup(Long id) {
        throwGroupNotExistById(id);
        return groupService.getGroupAndPermissions(id);
    }

    @Override
    public boolean createGroup(NewGroupDTO validator) {

        return false;
    }

    @Override
    public boolean updateGroup(Long id, UpdateGroupDTO validator) {
        return false;
    }

    @Override
    public boolean deleteGroup(Long id) {
        throwGroupNotExistById(id);
        return groupService.removeById(id);
    }

    @Override
    public boolean dispatchAuth(DispatchAuthDTO validator) {
        return false;
    }

    @Override
    public boolean dispatchAuths(DispatchAuthsDTO validator) {
        return false;
    }

    @Override
    public boolean removeAuths(RemoveAuthsDTO validator) {
        return false;
    }

    @Override
    public List<GroupDO> getAllGroups() {
        return groupService.list();
    }

    private void throwUserNotExistById(Long id) {
        boolean exist = userService.checkUserExistById(id);
        if (!exist) {
            throw new NotFound("未找到用户");
        }
    }

    private void throwGroupNotExistById(Long id) {
        boolean exist = groupService.checkGroupExistById(id);
        if (!exist) {
            throw new NotFound("未找到分组");
        }
    }
}
