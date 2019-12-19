package com.lin.cms.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.common.LocalUser;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.dto.user.ChangePasswordDTO;
import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.dto.user.UpdateInfoDTO;
import com.lin.cms.demo.mapper.UserGroupMapper;
import com.lin.cms.demo.mapper.UserMapper;
import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.demo.model.PermissionDO;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.model.UserGroupDO;
import com.lin.cms.demo.service.GroupService;
import com.lin.cms.demo.service.PermissionService;
import com.lin.cms.demo.service.UserIdentityService;
import com.lin.cms.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.exception.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private UserIdentityService userIdentityService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Transactional
    @Override
    public UserDO createUser(RegisterDTO dto) {
        boolean exist = this.checkUserExistByUsername(dto.getUsername());
        if (exist) {
            throw new ForbiddenException("username already exist, please choose a new one", 10071);
        }
        UserDO user = new UserDO();
        BeanUtil.copyProperties(dto, user);
        this.baseMapper.insert(user);
        if (dto.getGroupIds() != null && !dto.getGroupIds().isEmpty()) {
            checkGroupsExist(dto.getGroupIds());
            List<UserGroupDO> relations = dto.getGroupIds()
                    .stream()
                    .map(groupId -> new UserGroupDO(user.getId(), groupId))
                    .collect(Collectors.toList());
            userGroupMapper.insertBatch(relations);
        }
        userIdentityService.createUsernamePasswordIdentity(user.getId(), dto.getUsername(), dto.getPassword());
        return user;
    }

    @Transactional
    @Override
    public UserDO updateUserInfo(UpdateInfoDTO dto) {
        UserDO user = LocalUser.getLocalUser();
        if (dto.getUsername() != null && Strings.isNotBlank(dto.getUsername())) {
            boolean exist = this.checkUserExistByUsername(dto.getUsername());
            if (exist) {
                throw new ForbiddenException("username already exist, please choose a new one", 10071);
            }
            user.setUsername(dto.getUsername());
            userIdentityService.changeUsername(user.getId(), dto.getUsername());
        }
        BeanUtil.copyProperties(dto, user);
        this.baseMapper.updateById(user);
        return user;
    }

    @Override
    public UserDO changeUserPassword(ChangePasswordDTO dto) {
        UserDO user = LocalUser.getLocalUser();
        boolean valid = userIdentityService.verifyUsernamePassword(user.getId(), user.getUsername(), dto.getOldPassword());
        if (!valid) {
            throw new ParameterException("password invalid, please enter correct password", 10032);
        }
        valid = userIdentityService.changePassword(user.getId(), dto.getNewPassword());
        if (!valid) {
            throw new FailedException("password change failed", 10011);
        }
        return user;
    }

    @Override
    public List<GroupDO> getUserGroups(Long userId) {
        return groupService.getUserGroupsByUserId(userId);
    }

    @Override
    public List<Map<String, List<Map<String, String>>>> getStructualUserPermissions(Long userId) {
        List<PermissionDO> permissions = getUserPermissions(userId);
        return permissionService.structuringPermissions(permissions);
    }

    @Override
    public List<PermissionDO> getUserPermissions(Long userId) {
        // 查找用户搜索分组，查找分组下的所有权限
        List<Long> groupIds = groupService.getUserGroupIdsByUserId(userId);
        if (groupIds == null || groupIds.size() == 0) {
            return new ArrayList<>();
        }
        return permissionService.getPermissionByGroupIds(groupIds);
    }

    @Override
    public UserDO getUserByUsername(String username) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDO::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public boolean checkUserExistByUsername(String username) {
        int rows = this.baseMapper.selectCountByUsername(username);
        return rows > 0;
    }

    @Override
    public boolean checkUserExistById(Long id) {
        int rows = this.baseMapper.selectCountById(id);
        return rows > 0;
    }

    @Override
    public IPage<UserDO> getUserPageByGroupId(Page pager, Long groupId) {
        return this.baseMapper.selectPageByGroupId(pager, groupId);
    }

    private void checkGroupsExist(List<Long> ids) {
        for (long id : ids) {
            if (!groupService.checkGroupExistById(id)) {
                throw new NotFoundException("group not found，can't create user", 10023);
            }
        }
    }
}
