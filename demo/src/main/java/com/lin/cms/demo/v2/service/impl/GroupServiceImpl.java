package com.lin.cms.demo.v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.bo.GroupPermissionsBO;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.v2.model.GroupDO;
import com.lin.cms.demo.v2.mapper.GroupMapper;
import com.lin.cms.demo.v2.model.PermissionDO;
import com.lin.cms.demo.v2.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.demo.v2.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("groupServiceImpl-v2")
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    public static final String ROOT_GROUP_NAME = "root";

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<GroupDO> getUserGroupsByUserId(Long userId) {
        return this.baseMapper.selectUserGroups(userId);
    }

    @Override
    public List<Long> getUserGroupIdsByUserId(Long userId) {
        return this.baseMapper.selectUserGroupIDs(userId);
    }

    @Override
    public IPage<GroupDO> getGroupPage(long page, long count) {
        Page pager = new Page(page, count);
        return this.baseMapper.selectPage(pager, null);
    }

    @Override
    public boolean checkGroupExistById(Long id) {
        return this.baseMapper.selectCountById(id) > 0;
    }

    @Override
    public GroupPermissionsBO getGroupAndPermissions(Long id) {
        GroupDO group = this.baseMapper.selectById(id);
        List<PermissionDO> permissions = permissionService.getPermissionByGroupId(id);
        return new GroupPermissionsBO(group, permissions);
    }

    @Override
    public boolean checkGroupExistByName(String name) {
        QueryWrapper<GroupDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(GroupDO::getName, name);
        return this.baseMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean checkIsRootByUserId(Long userId) {
        return this.baseMapper.checkUserInGroupByUserIdAndGroupName(userId, ROOT_GROUP_NAME) > 0;
    }
}
