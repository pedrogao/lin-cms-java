package com.lin.cms.interfaces;

import com.lin.cms.model.BaseAuthModel;

public interface BaseAuthMapper {
    BaseAuthModel selectOneByGroupIdAndAuthAndModule(Integer groupId, String auth, String module);
}
