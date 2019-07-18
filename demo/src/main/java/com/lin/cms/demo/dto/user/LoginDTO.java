package com.lin.cms.demo.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "用户名不可为空")
    private String nickname;

    @NotBlank(message = "密码不可为空")
    private String password;
}
