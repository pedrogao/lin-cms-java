package com.lin.cms.demo.dto.user;

import com.lin.cms.demo.validator.EqualField;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@EqualField(srcField = "password", dstField = "confirmPassword", message = "两次输入密码不一致")
public class RegisterDTO {

    @NotBlank(message = "昵称不可为空")
    @Size(min = 2, max = 10, message = "昵称长度必须在2~10之间")
    private String nickname;

    @NotNull(message = "分组id不可为空")
    @Min(value = 1, message = "分组id必须是整数，且大于0")
    private Integer groupId;

    @Email(message = "电子邮箱不符合规范，请输入正确的邮箱")
    private String email;

    @NotBlank(message = "密码不可为空")
    @Pattern(regexp = "^[A-Za-z0-9_*&$#@]{6,22}$", message = "密码长度必须在6~22位之间，包含字符、数字和 _")
    private String password;

    @NotBlank(message = "确认密码不可为空")
    private String confirmPassword;


}
