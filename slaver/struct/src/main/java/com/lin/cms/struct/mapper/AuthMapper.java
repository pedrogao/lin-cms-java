package com.lin.cms.struct.mapper;

import com.lin.cms.struct.model.AuthDO;
import com.lin.cms.struct.model.SimpleAuthDO;
import com.lin.cms.db.CrudMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper extends CrudMapper<AuthDO> {
    AuthDO selectOneByGroupIdAndAuthAndModule(@Param("groupId") Integer groupId,
                                              @Param("auth") String auth,
                                              @Param("module") String module);

    List<SimpleAuthDO> findByGroupId(@Param("groupId") Integer groupId);

    void deleteByGroupId(@Param("groupId") Integer groupId);

    void deleteByGroupIdAndInAuths(@Param("groupId") Integer groupId, @Param("auths") List<String> auths);

    AuthDO findOneByGroupIdAndAuth(@Param("groupId") Integer groupId, @Param("auth") String auth);
}