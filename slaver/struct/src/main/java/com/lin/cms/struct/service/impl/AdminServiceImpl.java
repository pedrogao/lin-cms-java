package com.lin.cms.struct.service.impl;

import com.github.pagehelper.PageHelper;
import com.lin.cms.struct.mapper.AuthMapper;
import com.lin.cms.struct.mapper.GroupMapper;
import com.lin.cms.struct.mapper.UserMapper;
import com.lin.cms.struct.model.*;
import com.lin.cms.struct.service.AdminService;
import com.lin.cms.struct.validators.admin.*;
import com.lin.cms.struct.view.GroupWithAuthsVO;
import com.lin.cms.beans.CollectMetaPostBeanProcessor;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.exception.Forbidden;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;
import com.lin.cms.core.result.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public PageResult getUsers(Integer groupId, Integer count, Integer page) {
        // start: start * count1
        List<UserAndGroupNameDO> usersAndGroupName = userMapper.findUsersAndGroupName(groupId, page * count, count);
        Integer totalNums = userMapper.getCommonUsersCount(groupId);
        return PageResult.genPageResult(totalNums, usersAndGroupName);
    }

    @Override
    public void changeUserPassword(Integer id, ResetPasswordValidator validator) throws NotFound {
        UserDO user = userMapper.findOneUserByIdAndDeleteTime(id);
        if (user == null) {
            throw new NotFound("用户不存在");
        }
        user.setPasswordEncrypt(validator.getNewPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void deleteUser(Integer id) throws NotFound {
        UserDO user = userMapper.findOneUserByIdAndDeleteTime(id);
        if (user == null) {
            throw new NotFound("用户不存在");
        }
        // 软删除
        userMapper.softDeleteById(user.getId());
    }

    @Override
    public void updateUserInfo(Integer id, UpdateUserInfoValidator validator) throws NotFound, Parameter {
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
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public PageResult getGroups(Integer page, Integer count) {
        // 与其它语言保持一致，0为第一页
        PageHelper.startPage(page + 1, count);
        List<GroupDO> groups = groupMapper.selectAll();
        Integer total = groupMapper.getCount();
        List<GroupWithAuthsVO> groupAndAuths = new ArrayList<>();

        groups.forEach(group -> {
            GroupWithAuthsVO tmp = new GroupWithAuthsVO();
            BeanUtils.copyProperties(group, tmp);
            List<SimpleAuthDO> auths = authMapper.findByGroupId(group.getId());
            tmp.setAuths(auths);
            groupAndAuths.add(tmp);
        });
        return new PageResult(total, groupAndAuths);
    }

    @Override
    public GroupWithAuthsVO getGroup(Integer id) {
        GroupDO group = groupMapper.selectByPrimaryKey(id);
        GroupWithAuthsVO tmp = new GroupWithAuthsVO();
        BeanUtils.copyProperties(group, tmp);
        List<SimpleAuthDO> auths = authMapper.findByGroupId(group.getId());
        tmp.setAuths(auths);
        return tmp;
    }

    @Transactional
    @Override
    public void createGroup(NewGroupValidator validator) throws Forbidden {
        GroupDO exist = groupMapper.findOneByName(validator.getName());
        if (exist != null) {
            throw new Forbidden("分组已存在，不可创建同名分组");
        }
        GroupDO group = new GroupDO();
        group.setName(validator.getName());
        group.setInfo(validator.getInfo());
        groupMapper.insertSelective(group);
        Integer groupId = group.getId();
        validator.getAuths().forEach(item -> {
            AuthDO auth = new AuthDO();
            RouteMeta meta = postProcessor.findMetaByAuth(item);
            if (meta != null) {
                auth.setGroupId(groupId);
                auth.setAuth(meta.auth());
                auth.setModule(meta.module());
                authMapper.insertSelective(auth);
            }
        });
    }

    @Override
    public void updateGroup(Integer id, UpdateGroupValidator validator) throws NotFound {
        GroupDO group = groupMapper.selectByPrimaryKey(id);
        if (group == null) {
            throw new NotFound("分组不存在，更新失败");
        }
        group.setName(validator.getName());
        group.setInfo(validator.getInfo());
        groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public void deleteGroup(Integer id) throws NotFound, Forbidden {
        GroupDO group = groupMapper.selectByPrimaryKey(id);
        if (group == null) {
            throw new NotFound("分组不存在，删除失败");
        }
        UserDO userByGroupId = userMapper.findOneUserByGroupId(id);
        if (userByGroupId != null) {
            throw new Forbidden("分组下存在用户，不可删除");
        }
        authMapper.deleteByGroupId(id);
    }

    @Override
    public void dispatchAuth(DispatchAuthValidator validator) throws NotFound, Forbidden {
        GroupDO group = groupMapper.selectByPrimaryKey(validator.getGroupId());
        if (group == null) {
            throw new NotFound("分组不存在");
        }
        AuthDO one = authMapper.findOneByGroupIdAndAuth(validator.getGroupId(), validator.getAuth());
        if (one != null) {
            throw new Forbidden("已有权限，不可重复添加");
        }
        AuthDO auth = new AuthDO();
        RouteMeta meta = postProcessor.findMetaByAuth(validator.getAuth());
        auth.setModule(meta.module());
        auth.setAuth(meta.auth());
        auth.setGroupId(validator.getGroupId());
        authMapper.insertSelective(auth);
    }

    @Override
    public void dispatchAuths(DispatchAuthsValidator validator) throws NotFound {
        GroupDO group = groupMapper.selectByPrimaryKey(validator.getGroupId());
        if (group == null) {
            throw new NotFound("分组不存在");
        }
        validator.getAuths().forEach(item -> {
            AuthDO one = authMapper.findOneByGroupIdAndAuth(validator.getGroupId(), item);
            if (one != null) {
                AuthDO auth = new AuthDO();
                RouteMeta meta = postProcessor.findMetaByAuth(item);
                auth.setAuth(meta.auth());
                auth.setModule(meta.module());
                auth.setGroupId(validator.getGroupId());
                authMapper.insertSelective(auth);
            }
        });
    }

    @Override
    @Transactional
    public void removeAuths(RemoveAuthsValidator validator) throws NotFound {
        GroupDO group = groupMapper.selectByPrimaryKey(validator.getGroupId());
        if (group == null) {
            throw new NotFound("分组不存在");
        }
        authMapper.deleteByGroupIdAndInAuths(validator.getGroupId(), validator.getAuths());
    }
}
