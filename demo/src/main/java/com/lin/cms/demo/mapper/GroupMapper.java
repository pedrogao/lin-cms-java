package com.lin.cms.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.model.GroupAndAuthDO;
import com.lin.cms.demo.model.GroupDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper extends BaseMapper<GroupDO> {

    List<GroupAndAuthDO> findGroupsWithAuths();

    Integer getCount();

    GroupDO findOneByName(@Param("name") String name);
}