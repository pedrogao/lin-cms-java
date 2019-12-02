package com.lin.cms.demo.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class UpdateInfoDTO {

    @NotBlank(message = "邮箱不可为空")
    @Email(message = "电子邮箱不符合规范，请输入正确的邮箱")
    private String email;

    @NotBlank(message = "用户昵称不可为空")
    private String nickname;

    @NotBlank(message = "用户名不可为空")
    private String username;
}
