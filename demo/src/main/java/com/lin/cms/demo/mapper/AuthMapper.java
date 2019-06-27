package com.lin.cms.demo.mapper;

import com.lin.cms.db.CrudMapper;
import com.lin.cms.demo.model.AuthPO;
import com.lin.cms.demo.model.SimpleAuthPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper extends CrudMapper<AuthPO> {
    AuthPO selectOneByGroupIdAndAuthAndModule(@Param("groupId") Integer groupId,
                                              @Param("auth") String auth,
                                              @Param("module") String module);

    List<SimpleAuthPO> findByGroupId(@Param("groupId") Integer groupId);

    void deleteByGroupId(@Param("groupId") Integer groupId);

    void deleteByGroupIdAndInAuths(@Param("groupId") Integer groupId, @Param("auths") List<String> auths);

    AuthPO findOneByGroupIdAndAuth(@Param("groupId") Integer groupId, @Param("auth") String auth);
}