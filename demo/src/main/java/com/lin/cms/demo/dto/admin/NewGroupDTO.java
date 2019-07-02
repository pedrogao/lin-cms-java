package com.lin.cms.demo.dto.admin;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class NewGroupDTO {


    @NotBlank(message = "请输入分组名称")
    private String name;

    private String info;

    @NotBlank(message = "请输入auths字段")
    @Valid
    private List<String> auths;
}
