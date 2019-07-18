package com.lin.cms.demo.dto.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Setter
@Getter
@NoArgsConstructor
public class UpdateUserInfoDTO {

    @Min(value = 1, message = "分组id必须是整数，且大于0")
    private Integer groupId;

    @Email(message = "电子邮箱不符合规范，请输入正确的邮箱")
    private String email;
}
