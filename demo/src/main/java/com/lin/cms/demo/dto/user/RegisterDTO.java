package com.lin.cms.demo.dto.user;

import com.lin.cms.demo.common.validator.EqualField;
import com.lin.cms.demo.common.validator.LongList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@EqualField(srcField = "password", dstField = "confirmPassword", message = "两次输入密码不一致")
public class RegisterDTO {

    @NotBlank(message = "用户名不可为空")
    @Size(min = 2, max = 10, message = "用户名长度必须在2~10之间")
    private String username;

    @NotNull(message = "分组id不可为空")
    @LongList(min = 1, message = "分组id必须是整数，且大于0")
    private List<Long> groupIds;

    @Email(message = "电子邮箱不符合规范，请输入正确的邮箱")
    private String email;

    @NotBlank(message = "密码不可为空")
    @Pattern(regexp = "^[A-Za-z0-9_*&$#@]{6,22}$", message = "密码长度必须在6~22位之间，包含字符、数字和 _")
    private String password;

    @NotBlank(message = "确认密码不可为空")
    private String confirmPassword;


}
