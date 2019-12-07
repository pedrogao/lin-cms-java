package com.lin.cms.demo.v2.mapper;

import com.lin.cms.demo.v2.model.UserGroupDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("userGroupMapper2")
public interface UserGroupMapper extends BaseMapper<UserGroupDO> {

    int insertBatch(@Param("relations") List<UserGroupDO> relations);
}
