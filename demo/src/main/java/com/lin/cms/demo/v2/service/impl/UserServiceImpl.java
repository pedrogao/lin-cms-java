package com.lin.cms.demo.v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.common.LocalUser;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.dto.user.ChangePasswordDTO;
import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.dto.user.UpdateInfoDTO;
import com.lin.cms.demo.v2.mapper.UserMapper;
import com.lin.cms.demo.v2.model.GroupDO;
import com.lin.cms.demo.v2.model.PermissionDO;
import com.lin.cms.demo.v2.model.UserDO;
import com.lin.cms.demo.v2.service.GroupService;
import com.lin.cms.demo.v2.service.PermissionService;
import com.lin.cms.demo.v2.service.UserIdentityService;
import com.lin.cms.demo.v2.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.exception.Failed;
import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.Parameter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("userServiceImpl-v2")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private UserIdentityService userIdentityService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PermissionService permissionService;

    @Transactional
    @Override
    public UserDO createUser(RegisterDTO dto) {
        // TODO groupId
        boolean exist = this.checkUserExistByUsername(dto.getUsername());
        if (exist) {
            throw new Forbidden("已经有用户使用了该名称，请重新输入新的用户名");
        }
        UserDO user = new UserDO();
        user.setUsername(dto.getUsername());
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        this.baseMapper.insert(user);
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
                throw new Forbidden("已经有用户使用了该名称，请重新输入新的用户名");
            }
            user.setUsername(dto.getUsername());
            userIdentityService.changeUsername(user.getId(), dto.getUsername());
        }
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        this.baseMapper.updateById(user);
        return user;
    }

    @Override
    public UserDO changeUserPassword(ChangePasswordDTO dto) {
        UserDO user = LocalUser.getLocalUser();
        boolean valid = userIdentityService.verifyUsernamePassword(user.getId(), user.getUsername(), dto.getOldPassword());
        if (!valid) {
            throw new Parameter("请输入正确的旧密码");
        }
        valid = userIdentityService.changePassword(user.getId(), dto.getNewPassword());
        if (!valid) {
            throw new Failed("更新密码失败");
        }
        return user;
    }

    @Override
    public List<GroupDO> getUserGroups(Long userId) {
        return groupService.getUserGroupsByUserId(userId);
    }

    @Override
    public List<Map<String, List<Map<String, String>>>> getUserPermissions(Long userId) {
        // 查找用户搜索分组，查找分组下的所有权限
        List<Long> groupIds = groupService.getUserGroupIdsByUserId(userId);
        List<PermissionDO> permissions = permissionService.getPermissionByGroupIds(groupIds);
        return permissionService.structuringPermissions(permissions);
    }

    @Override
    public UserDO findByUsername(String username) {
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
    public IPage<UserDO> findUsersByPage(Page pager, Long groupId) {
        return this.baseMapper.selectPageByGroupId(pager, groupId);
    }
}
