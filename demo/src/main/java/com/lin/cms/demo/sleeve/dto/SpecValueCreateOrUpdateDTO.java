package com.lin.cms.demo.sleeve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.lin.cms.demo.validator.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
public class SpecValueCreateOrUpdateDTO {
    @NotBlank(message = "规格值名不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "规格值名长度不能超过255字符")
    private String value;

    @Length(min = 1, max = 255, allowBlank = true, message = "规格值扩展描述长度不能超过255字符")
    private String extend;

    @Positive(message = "规格键id必须为正整数")
    @NotNull(message = "规格键id不可为空")
    private Long specId;
}
