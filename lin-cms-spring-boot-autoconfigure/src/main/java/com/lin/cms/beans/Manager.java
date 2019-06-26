package com.lin.cms.beans;

import com.lin.cms.interfaces.*;

public class Manager {

    private BaseUserMapper userMapper;

    private BaseAuthMapper authMapper;

    private BaseGroupMapper groupMapper;

//    private BaseLogMapper logMapper;
//
//    private BaseFileMapper fileMapper;
//
//    public Manager(BaseUserMapper userMapper, BaseGroupMapper groupMapper,
//                   BaseAuthMapper authMapper, BaseLogMapper logMapper,
//                   BaseFileMapper fileMapper) {
//        this.userMapper = userMapper;
//        this.authMapper = authMapper;
//        this.groupMapper = groupMapper;
//        this.logMapper = logMapper;
//        this.fileMapper = fileMapper;
//    }

    public Manager(BaseUserMapper userMapper, BaseGroupMapper groupMapper,
                   BaseAuthMapper authMapper) {
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

//    public BaseLogMapper getLogMapper() {
//        return logMapper;
//    }
//
//    public void setLogMapper(BaseLogMapper logMapper) {
//        this.logMapper = logMapper;
//    }
//
//    public BaseFileMapper getFileMapper() {
//        return fileMapper;
//    }
//
//    public void setFileMapper(BaseFileMapper fileMapper) {
//        this.fileMapper = fileMapper;
//    }
}
