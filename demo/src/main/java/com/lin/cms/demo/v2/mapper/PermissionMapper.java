package com.lin.cms.demo.v2.mapper;

import com.lin.cms.demo.v2.model.PermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("permissionMapper2")
public interface PermissionMapper extends BaseMapper<PermissionDO> {

    /**
     * 通过分组ids得到所有分组下的权限
     *
     * @param groupIDs 分组ids
     * @return 权限
     */
    List<PermissionDO> selectPermissionsByGroupIds(List<Long> groupIDs);

    /**
     * 通过分组id得到所有分组下的权限
     *
     * @param groupID 分组id
     * @return 权限
     */
    List<PermissionDO> selectPermissionsByGroupId(Long groupID);
}
