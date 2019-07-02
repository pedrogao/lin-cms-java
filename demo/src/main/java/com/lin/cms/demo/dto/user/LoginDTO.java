package com.lin.cms.demo.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @NotBlank(message = "用户名不可为空")
    private String nickname;

    @NotBlank(message = "密码不可为空")
    private String password;
}
