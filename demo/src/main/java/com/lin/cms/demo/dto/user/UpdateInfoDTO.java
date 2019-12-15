package com.lin.cms.demo.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UpdateInfoDTO {

    @Email(message = "电子邮箱不符合规范，请输入正确的邮箱")
    private String email;

    @Size(min = 2, max = 10, message = "用户名长度必须在2~10之间")
    private String nickname;

    @Size(min = 2, max = 10, message = "用户名长度必须在2~10之间")
    private String username;

    @Size(max = 500, message = "头像链接长度不能超过500字符")
    private String avatar;
}
