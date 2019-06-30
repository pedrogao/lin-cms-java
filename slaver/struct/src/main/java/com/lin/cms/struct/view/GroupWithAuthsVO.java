package com.lin.cms.struct.view;

import com.lin.cms.struct.model.SimpleAuthDO;
import lombok.Data;

import java.util.List;

@Data
public class GroupWithAuthsVO {
    private Integer id;

    private String name;

    private String info;

    private List<SimpleAuthDO> auths;
}
