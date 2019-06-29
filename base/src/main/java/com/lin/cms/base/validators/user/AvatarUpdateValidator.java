package com.lin.cms.base.validators.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AvatarUpdateValidator {
    @NotBlank(message = "必须传入头像的url链接")
    private String avatar;

}
