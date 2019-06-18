package com.lin.cms.demo.view;

import com.lin.cms.demo.model.SimpleAuth;
import lombok.Data;

import java.util.List;

@Data
public class GroupWithAuths {
    private Integer id;

    private String name;

    private String info;

    private List<SimpleAuth> auths;
}
