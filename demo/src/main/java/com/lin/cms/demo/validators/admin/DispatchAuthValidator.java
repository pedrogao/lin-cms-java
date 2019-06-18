package com.lin.cms.demo.validators.admin;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DispatchAuthValidator {

    @Min(value = 1, message = "分组id必须正整数")
    @NotNull(message = "分组id不可为空")
    private Integer groupId;

    @NotBlank
    private String auth;
}
