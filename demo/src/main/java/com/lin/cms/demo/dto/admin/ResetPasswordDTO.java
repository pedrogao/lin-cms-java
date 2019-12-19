package com.lin.cms.demo.dto.admin;

import com.lin.cms.demo.common.validator.EqualField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualField(srcField = "newPassword", dstField = "confirmPassword", message = "{password.equal-field}")
@Setter
@Getter
@NoArgsConstructor
public class ResetPasswordDTO {

    @NotBlank(message = "{password.new-password.not-blank}")
    @Pattern(regexp = "^[A-Za-z0-9_*&$#@]{6,22}$", message = "{password.new-password.pattern}")
    private String newPassword;

    @NotBlank(message = "{password.confirm-password.not-blank}")
    private String confirmPassword;
}
