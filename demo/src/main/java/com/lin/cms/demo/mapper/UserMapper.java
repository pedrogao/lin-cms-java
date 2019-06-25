package com.lin.cms.demo.mapper;

import com.lin.cms.demo.db.CrudMapper;
import com.lin.cms.interfaces.BaseUserMapper;
import com.lin.cms.demo.model.UserPO;
import com.lin.cms.demo.model.UserAndGroupNamePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseUserMapper, CrudMapper<UserPO> {

    List<UserAndGroupNamePO> findUsersAndGroupName(@Param("groupId") Integer groupId,
                                                   @Param("page") int page,
                                                   @Param("count") int count);

    Integer getCommonUsersCount(@Param("groupId") Integer groupId);

    UserPO findOneUserByIdAndDeleteTime(@Param("id") Integer id);

    void softDeleteById(@Param("id") Integer id);

    UserPO findOneUserByEmailAndDeleteTime(@Param("email") String email);

    UserPO findOneUserByGroupId(@Param("groupId") Integer groupId);

}