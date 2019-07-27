package com.lin.cms.demo.sleeve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
public class SkuSelector {

    @Positive(message = "规格键名id必须为正整数")
    @NotNull(message = "规格键名id不可为空")
    private Long keyId;

    @Positive(message = "规格值名id必须为正整数")
    @NotNull(message = "规格值名id不可为空")
    private Long valueId;

    @NotBlank(message = "规格键名不可为空")
    @Length(min = 1, max = 100, message = "规格值名长度不能超过100字符")
    private String key;

    @NotBlank(message = "规格值名不可为空")
    @Length(min = 1, max = 100, message = "规格值名长度不能超过100字符")
    private String value;
}
