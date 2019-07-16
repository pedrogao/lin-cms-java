package com.lin.cms.demo.mapper;

import com.lin.cms.db.CrudMapper;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.model.UserAndGroupNameDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends CrudMapper<UserDO> {

    List<UserAndGroupNameDO> findUsersAndGroupName(@Param("groupId") Integer groupId,
                                                   @Param("page") int page,
                                                   @Param("count") int count);

    Integer getCommonUsersCount(@Param("groupId") Integer groupId);

    UserDO findOneUserByIdAndDeleteTime(@Param("id") Integer id);

    void softDeleteById(@Param("id") Integer id);

    UserDO findOneUserByEmailAndDeleteTime(@Param("email") String email);

    // 用户检测分组下是否存在用户
    UserDO findOneUserByGroupId(@Param("groupId") Integer groupId);

}