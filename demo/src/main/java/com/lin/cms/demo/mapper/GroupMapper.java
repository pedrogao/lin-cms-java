package com.lin.cms.demo.mapper;

import com.lin.cms.db.CrudMapper;
import com.lin.cms.demo.model.GroupAndAuthPO;
import com.lin.cms.demo.model.GroupPO;
import com.lin.cms.interfaces.BaseGroupMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper extends BaseGroupMapper, CrudMapper<GroupPO> {

    List<GroupAndAuthPO> findGroupsWithAuths();

    Integer getCount();

    GroupPO findOneByName(@Param("name") String name);
}