package com.lin.cms.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.model.AuthDO;
import com.lin.cms.demo.model.SimpleAuthDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper extends BaseMapper<AuthDO> {

    AuthDO selectOneByGroupIdAndAuthAndModule(@Param("groupId") Long groupId,
                                              @Param("permission") String auth,
                                              @Param("module") String module);

    List<SimpleAuthDO> findByGroupId(@Param("groupId") Long groupId);

    void deleteByGroupId(@Param("groupId") Long groupId);

    void deleteByGroupIdAndInAuths(@Param("groupId") Long groupId, @Param("auths") List<String> auths);

    AuthDO findOneByGroupIdAndAuth(@Param("groupId") Long groupId, @Param("permission") String auth);
}