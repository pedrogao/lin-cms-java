package com.lin.cms.demo.v2.mapper;

import com.lin.cms.demo.v2.model.GroupPermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("groupPermissionMapper2")
public interface GroupPermissionMapper extends BaseMapper<GroupPermissionDO> {

    int insertBatch(List<GroupPermissionDO> relations);

    int deleteBatchByGroupIdAndPermissionId(@Param("groupId") Long groupId, @Param("permissionIds") List<Long> permissionIds);
}
