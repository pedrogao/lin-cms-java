package com.lin.cms.demo.mapper;

import com.lin.cms.demo.db.Mapper;
import com.lin.cms.demo.model.Auth;
import com.lin.cms.demo.model.SimpleAuth;
import com.lin.cms.interfaces.BaseAuthMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper extends BaseAuthMapper, Mapper<Auth> {
    Auth selectOneByGroupIdAndAuthAndModule(@Param("groupId") Integer groupId,
                                            @Param("auth") String auth,
                                            @Param("module") String module);

    List<SimpleAuth> findByGroupId(@Param("groupId") Integer groupId);

    void deleteByGroupId(@Param("groupId") Integer groupId);

    void deleteByGroupIdAndInAuths(@Param("groupId") Integer groupId, @Param("auths") List<String> auths);

    Auth findOneByGroupIdAndAuth(@Param("groupId") Integer groupId, @Param("auth") String auth);
}