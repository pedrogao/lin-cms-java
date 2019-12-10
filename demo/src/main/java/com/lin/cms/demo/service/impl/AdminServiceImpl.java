package com.lin.cms.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.NotFound;
import com.lin.cms.exception.Parameter;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.mapper.AuthMapper;
import com.lin.cms.demo.mapper.GroupMapper;
import com.lin.cms.demo.mapper.UserMapper;
import com.lin.cms.demo.service.AdminService;
import com.lin.cms.demo.bo.GroupAuthsBO;
import com.lin.cms.beans.CollectMetaPostBeanProcessor;
import com.lin.cms.demo.model.*;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.common.AuthSpliter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private CollectMetaPostBeanProcessor postProcessor;

    @Override
    public PageResult getUsers(Long groupId, Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<UserAndGroupNameDO> iPage = userMapper.findUsersAndGroupName(pager, groupId);
        List<UserAndGroupNameDO> usersAndGroupName = iPage.getRecords();
        Integer total = userMapper.getCommonUsersCount(groupId);
        return PageResult.genPageResult(total, usersAndGroupName, page, count);
    }

    @Override
    public void changeUserPassword(Long id, ResetPasswordDTO validator) {
        UserDO user = userMapper.findOneUserByIdAndDeleteTime(id);
        if (user == null) {
            throw new NotFound("用户不存在");
        }
        user.setPasswordEncrypt(validator.getNewPassword());
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long id) {
        UserDO user = userMapper.findOneUserByIdAndDeleteTime(id);
        if (user == null) {
            throw new NotFound("用户不存在");
        }
        // 软删除
        userMapper.softDeleteById(user.getId());
    }

    @Override
    public void updateUserInfo(Long id, UpdateUserInfoDTO validator) {
        UserDO user = userMapper.findOneUserByIdAndDeleteTime(id);
        if (user == null) {
            throw new NotFound("用户不存在");
        }
        if (!user.getEmail().equals(validator.getEmail())) {
            UserDO exist = userMapper.findOneUserByEmailAndDeleteTime(validator.getEmail());
            if (exist != null) {
                throw new Parameter("邮箱已被注册，请重新输入邮箱");
            }
        }
        user.setGroupId(validator.getGroupId());
        user.setEmail(validator.getEmail());
        userMapper.updateById(user);
    }

    @Override
    public PageResult getGroups(Long page, Long count) {
        Page<GroupDO> pager = new Page<>(page, count);
        IPage<GroupDO> iPage = groupMapper.selectPage(pager, null);
        List<GroupDO> groups = iPage.getRecords();
        long total = iPage.getTotal();
        List<GroupAuthsBO> groupAndAuths = new ArrayList<>();

        groups.forEach(group -> {
            GroupAuthsBO tmp = new GroupAuthsBO();
            BeanUtils.copyProperties(group, tmp);
            List<SimpleAuthDO> auths = authMapper.findByGroupId(group.getId());
            tmp.setAuths(auths);
            groupAndAuths.add(tmp);
        });
        return new PageResult(total, groupAndAuths, page, count);
    }

    @Override
    public GroupAuthsBO getGroup(Long id) {
        GroupDO group = groupMapper.selectById(id);
        GroupAuthsBO tmp = new GroupAuthsBO();
        BeanUtils.copyProperties(group, tmp);
        List<SimpleAuthDO> auths = authMapper.findByGroupId(group.getId());
        List<Map<String, List<Map<String, String>>>> structualAuths = AuthSpliter.splitAuths(auths);
        tmp.setAuths(structualAuths);
        return tmp;
    }

    @Transactional
    @Override
    public void createGroup(NewGroupDTO validator) {
        GroupDO exist = groupMapper.findOneByName(validator.getName());
        if (exist != null) {
            throw new Forbidden("分组已存在，不可创建同名分组");
        }
        GroupDO group = new GroupDO();
        group.setName(validator.getName());
        group.setInfo(validator.getInfo());
        groupMapper.insert(group);
        Long groupId = group.getId();
        // validator.getAuths().forEach(item -> {
        //     AuthDO permission = new AuthDO();
        //     RouteMeta meta = postProcessor.findMetaByAuth(item);
        //     if (meta != null) {
        //         permission.setGroupId(groupId);
        //         permission.setAuth(meta.permission());
        //         permission.setModule(meta.module());
        //         authMapper.insert(permission);
        //     }
        // });
    }

    @Override
    public void updateGroup(Long id, UpdateGroupDTO validator) {
        GroupDO group = groupMapper.selectById(id);
        if (group == null) {
            throw new NotFound("分组不存在，更新失败");
        }
        group.setName(validator.getName());
        group.setInfo(validator.getInfo());
        groupMapper.updateById(group);
    }

    @Override
    public void deleteGroup(Long id) {
        GroupDO group = groupMapper.selectById(id);
        if (group == null) {
            throw new NotFound("分组不存在，删除失败");
        }
        UserDO userByGroupId = userMapper.findOneUserByGroupId(id);
        if (userByGroupId != null) {
            throw new Forbidden("分组下存在用户，不可删除");
        }
        // 删除 auths
        authMapper.deleteByGroupId(id);
        // 删除分组
        groupMapper.deleteById(group.getId());
    }

    @Override
    public void dispatchAuth(DispatchPermissionDTO validator) {
        GroupDO group = groupMapper.selectById(validator.getGroupId());
        if (group == null) {
            throw new NotFound("分组不存在");
        }
        // AuthDO one = authMapper.findOneByGroupIdAndAuth(validator.getGroupId(), validator.getAuth());
        // if (one != null) {
        //     throw new Forbidden("已有权限，不可重复添加");
        // }
        // AuthDO permission = new AuthDO();
        // RouteMeta meta = postProcessor.findMetaByAuth(validator.getAuth());
        // permission.setModule(meta.module());
        // permission.setAuth(meta.permission());
        // permission.setGroupId(validator.getGroupId());
        // authMapper.insert(permission);
    }

    @Override
    public void dispatchAuths(DispatchPermissionsDTO validator) {
        GroupDO group = groupMapper.selectById(validator.getGroupId());
        if (group == null) {
            throw new NotFound("分组不存在");
        }
        // validator.getAuths().forEach(item -> {
        //     AuthDO one = authMapper.findOneByGroupIdAndAuth(validator.getGroupId(), item);
        //     if (one == null) {
        //         AuthDO permission = new AuthDO();
        //         RouteMeta meta = postProcessor.findMetaByAuth(item);
        //         permission.setAuth(meta.permission());
        //         permission.setModule(meta.module());
        //         permission.setGroupId(validator.getGroupId());
        //         authMapper.insert(permission);
        //     }
        // });
    }

    @Override
    @Transactional
    public void removeAuths(RemovePermissionsDTO validator) {
        GroupDO group = groupMapper.selectById(validator.getGroupId());
        if (group == null) {
            throw new NotFound("分组不存在");
        }
        // authMapper.deleteByGroupIdAndInAuths(validator.getGroupId(), validator.getAuths());
    }

    @Override
    public List<GroupDO> getAllGroups() {
        List<GroupDO> groups = groupMapper.selectList(null);
        return groups;
    }
}
