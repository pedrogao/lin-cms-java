package com.lin.cms.demo.mapper;

import com.lin.cms.demo.db.Mapper;
import com.lin.cms.interfaces.BaseUserMapper;
import com.lin.cms.demo.model.User;
import com.lin.cms.demo.model.UserAndGroupName;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseUserMapper, Mapper<User> {

    List<UserAndGroupName> findUsersAndGroupName(@Param("groupId") Integer groupId,
                                                 @Param("page") int page,
                                                 @Param("count") int count);

    Integer getCommonUsersCount(@Param("groupId") Integer groupId);

    User findOneUserByIdAndDeleteTime(@Param("id") Integer id);

    void softDeleteById(@Param("id") Integer id);

    User findOneUserByEmailAndDeleteTime(@Param("email") String email);

    User findOneUserByGroupId(@Param("groupId") Integer groupId);

}