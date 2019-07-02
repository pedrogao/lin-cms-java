package com.lin.cms.demo.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateInfoDTO {

    @NotBlank(message = "邮箱不可为空")
    @Email(message = "电子邮箱不符合规范，请输入正确的邮箱")
    private String email;
}
