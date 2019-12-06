package com.lin.cms.demo.dto.admin;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DispatchPermissionDTO {

    @Min(value = 1, message = "分组id必须正整数")
    @NotNull(message = "分组id不可为空")
    private Long groupId;

    @NotBlank
    private Long permissionId;
}
