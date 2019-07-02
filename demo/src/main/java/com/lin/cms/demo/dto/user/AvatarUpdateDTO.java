package com.lin.cms.demo.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AvatarUpdateDTO {
    @NotBlank(message = "必须传入头像的url链接")
    private String avatar;

}
