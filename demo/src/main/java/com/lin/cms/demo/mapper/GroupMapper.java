package com.lin.cms.demo.mapper;

import com.lin.cms.demo.db.Mapper;
import com.lin.cms.demo.model.GroupAndAuth;
import com.lin.cms.demo.model.Group;
import com.lin.cms.interfaces.BaseGroupMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper extends BaseGroupMapper, Mapper<Group> {

    List<GroupAndAuth> findGroupsWithAuths();

    Integer getCount();

    Group findOneByName(@Param("name") String name);
}