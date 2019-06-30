package com.lin.cms.struct.validators.admin;

import com.lin.cms.struct.validators.base.EqualField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualField(srcField = "newPassword", dstField = "confirmPassword", message = "两次输入密码不一致")
@Data
public class ResetPasswordValidator {

    @NotBlank(message = "密码不可为空")
    @Pattern(regexp = "^[A-Za-z0-9_*&$#@]{6,22}$", message = "密码长度必须在6~22位之间，包含字符、数字和 _")
    private String newPassword;

    @NotBlank(message = "确认密码不可为空")
    private String confirmPassword;
}
