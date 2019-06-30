package com.lin.cms.demo.mapper;

import com.lin.cms.demo.model.GroupAndAuthDO;
import com.lin.cms.db.CrudMapper;
import com.lin.cms.demo.model.GroupDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper extends CrudMapper<GroupDO> {

    List<GroupAndAuthDO> findGroupsWithAuths();

    Integer getCount();

    GroupDO findOneByName(@Param("name") String name);
}