package com.lin.cms.struct.validators.admin;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUserInfoValidator {
    @NotNull(message = "分组id不可为空")
    @Min(value = 1, message = "分组id必须是整数，且大于0")
    private Integer groupId;

    @Email(message = "电子邮箱不符合规范，请输入正确的邮箱")
    private String email;
}
