package com.lin.cms.struct.mapper;

import com.lin.cms.struct.model.GroupAndAuthDO;
import com.lin.cms.struct.model.GroupDO;
import com.lin.cms.db.CrudMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper extends CrudMapper<GroupDO> {

    List<GroupAndAuthDO> findGroupsWithAuths();

    Integer getCount();

    GroupDO findOneByName(@Param("name") String name);
}