package com.lin.cms.base.view;

import com.lin.cms.base.model.SimpleAuthDO;
import lombok.Data;

import java.util.List;

@Data
public class GroupWithAuthsVO {
    private Integer id;

    private String name;

    private String info;

    private List<SimpleAuthDO> auths;
}
