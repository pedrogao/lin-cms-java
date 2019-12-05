package com.lin.cms.demo.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class GroupAuthsBO {
    private Integer id;

    private String name;

    private String info;

    private List auths;
}
