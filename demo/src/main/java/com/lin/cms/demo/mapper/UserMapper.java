package com.lin.cms.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.common.page.Page;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.model.UserAndGroupNameDO;
import org.apache.ibatis.annotations.Param;


public interface UserMapper extends BaseMapper<UserDO> {

    IPage<UserAndGroupNameDO> findUsersAndGroupName(Page page, @Param("groupId") Integer groupId);

    Integer getCommonUsersCount(@Param("groupId") Integer groupId);

    UserDO findOneUserByIdAndDeleteTime(@Param("id") Integer id);

    void softDeleteById(@Param("id") Integer id);

    UserDO findOneUserByEmailAndDeleteTime(@Param("email") String email);

    // 用户检测分组下是否存在用户
    UserDO findOneUserByGroupId(@Param("groupId") Integer groupId);

}