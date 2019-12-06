package com.lin.cms.demo.v2.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.bo.GroupPermissionsBO;
import com.lin.cms.demo.common.mybatis.Page;
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
    List<Long> getUserGroupIdsByUserId(Long userId);

    /**
     * 分页获取分组数据
     *
     * @param pager 分页
     * @return 分组
     */
    IPage<GroupDO> findGroupsByPage(Page pager);

    /**
     * 通过id检查分组是否存在
     *
     * @param id 分组id
     * @return 是否存在
     */
    boolean checkGroupExistById(Long id);

    /**
     * 获得分组及其权限
     *
     * @param id 分组id
     * @return 分组及权限
     */
    GroupPermissionsBO getGroupAndPermissions(Long id);
}
