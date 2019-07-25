package com.lin.cms.demo.model;

import lombok.Getter;
import lombok.Setter;

// setter getter
@Setter
@Getter
public class GroupAndAuthDO {
    private Long id;

    private String name;

    private String info;

    private String auth;

    private String module;
}
