package com.lin.cms.demo.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class AvatarUpdateDTO {
    @NotBlank(message = "必须传入头像的url链接")
    private String avatar;

}
