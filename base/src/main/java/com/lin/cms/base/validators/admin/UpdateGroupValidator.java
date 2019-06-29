package com.lin.cms.base.validators.admin;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateGroupValidator {
    @NotBlank(message = "请输入分组名称")
    private String name;

    private String info;
}
