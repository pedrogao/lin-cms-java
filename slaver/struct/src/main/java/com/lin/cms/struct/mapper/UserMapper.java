package com.lin.cms.struct.mapper;

import com.lin.cms.struct.model.UserAndGroupNameDO;
import com.lin.cms.struct.model.UserDO;
import com.lin.cms.db.CrudMapper;
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

    UserDO findOneUserByGroupId(@Param("groupId") Integer groupId);

}