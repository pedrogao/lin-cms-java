package com.lin.cms.demo.v2.service.impl;

import com.lin.cms.demo.v2.model.GroupDO;
import com.lin.cms.demo.v2.mapper.GroupMapper;
import com.lin.cms.demo.v2.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("groupServiceImpl-v2")
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {


    @Override
    public List<GroupDO> getUserGroupsByUserId(Long userId) {
        return this.baseMapper.selectUserGroups(userId);
    }

    @Override
    public List<Long> getUserGroupIDsByUserId(Long userId) {
        return this.baseMapper.selectUserGroupIDs(userId);
    }
}
