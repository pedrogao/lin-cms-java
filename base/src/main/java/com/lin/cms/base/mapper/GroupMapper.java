package com.lin.cms.base.mapper;

import com.lin.cms.base.model.GroupAndAuthDO;
import com.lin.cms.base.model.GroupDO;
import com.lin.cms.db.CrudMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper extends CrudMapper<GroupDO> {

    List<GroupAndAuthDO> findGroupsWithAuths();

    Integer getCount();

    GroupDO findOneByName(@Param("name") String name);
}