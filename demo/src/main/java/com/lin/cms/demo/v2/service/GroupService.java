package com.lin.cms.demo.v2.service;

import com.lin.cms.demo.v2.model.GroupDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author pedro
 * @since 2019-11-30
 */
public interface GroupService extends IService<GroupDO> {

    /**
     * 获得用户的所有分组
     *
     * @param userId 用户id
     * @return 所有分组
     */
    List<GroupDO> getUserGroupsByUserId(Long userId);

    /**
     * 获得用户的所有分组id
     *
     * @param userId 用户id
     * @return 所有分组id
     */
    List<Long> getUserGroupIDsByUserId(Long userId);

}
