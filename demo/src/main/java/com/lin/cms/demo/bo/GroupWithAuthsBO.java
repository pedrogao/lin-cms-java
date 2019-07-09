package com.lin.cms.demo.bo;

import lombok.Data;

import java.util.List;

@Data
public class GroupWithAuthsBO {
    private Integer id;

    private String name;

    private String info;

    private List auths;
}
