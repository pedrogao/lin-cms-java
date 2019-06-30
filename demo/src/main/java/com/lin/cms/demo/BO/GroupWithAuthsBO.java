package com.lin.cms.demo.BO;

import com.lin.cms.demo.model.SimpleAuthDO;
import lombok.Data;

import java.util.List;

@Data
public class GroupWithAuthsBO {
    private Integer id;

    private String name;

    private String info;

    private List<SimpleAuthDO> auths;
}
