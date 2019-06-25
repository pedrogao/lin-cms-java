package com.lin.cms.demo.view;

import com.lin.cms.demo.model.SimpleAuthPO;
import lombok.Data;

import java.util.List;

@Data
public class GroupWithAuthsVO {
    private Integer id;

    private String name;

    private String info;

    private List<SimpleAuthPO> auths;
}
