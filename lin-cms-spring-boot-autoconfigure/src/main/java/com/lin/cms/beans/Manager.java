package com.lin.cms.beans;

import com.lin.cms.interfaces.BaseAuthMapper;
import com.lin.cms.interfaces.BaseGroupMapper;
import com.lin.cms.interfaces.BaseUserMapper;

public class Manager {

    private BaseUserMapper userMapper;

    private BaseAuthMapper authMapper;

    private BaseGroupMapper groupMapper;

    public Manager(BaseUserMapper userMapper, BaseGroupMapper groupMapper, BaseAuthMapper authMapper) {
        this.userMapper = userMapper;
        this.authMapper = authMapper;
        this.groupMapper = groupMapper;
    }

    public Manager() {
    }

    public BaseUserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(BaseUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public BaseAuthMapper getAuthMapper() {
        return authMapper;
    }

    public void setAuthMapper(BaseAuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    public BaseGroupMapper getGroupMapper() {
        return groupMapper;
    }

    public void setGroupMapper(BaseGroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }
}
