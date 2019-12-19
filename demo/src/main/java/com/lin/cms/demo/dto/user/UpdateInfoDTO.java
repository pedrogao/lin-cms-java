package com.lin.cms.demo.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UpdateInfoDTO {

    @Email(message = "{email}")
    private String email;

    @Size(min = 2, max = 10, message = "{user.nickname.size}")
    private String nickname;

    @Size(min = 2, max = 10, message = "{user.username.size}")
    private String username;

    @Size(max = 500, message = "{user.avatar.size}")
    private String avatar;
}
