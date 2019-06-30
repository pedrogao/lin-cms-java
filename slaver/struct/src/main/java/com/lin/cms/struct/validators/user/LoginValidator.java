package com.lin.cms.struct.validators.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginValidator {
    @NotBlank(message = "用户名不可为空")
    private String nickname;

    @NotBlank(message = "密码不可为空")
    private String password;
}
