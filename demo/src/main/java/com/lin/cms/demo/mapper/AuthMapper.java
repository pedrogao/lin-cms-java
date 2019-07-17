package com.lin.cms.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.model.AuthDO;
import com.lin.cms.demo.model.SimpleAuthDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper extends BaseMapper<AuthDO> {
    AuthDO selectOneByGroupIdAndAuthAndModule(@Param("groupId") Integer groupId,
                                              @Param("auth") String auth,
                                              @Param("module") String module);

    List<SimpleAuthDO> findByGroupId(@Param("groupId") Integer groupId);

    void deleteByGroupId(@Param("groupId") Integer groupId);

    void deleteByGroupIdAndInAuths(@Param("groupId") Integer groupId, @Param("auths") List<String> auths);

    AuthDO findOneByGroupIdAndAuth(@Param("groupId") Integer groupId, @Param("auth") String auth);
}