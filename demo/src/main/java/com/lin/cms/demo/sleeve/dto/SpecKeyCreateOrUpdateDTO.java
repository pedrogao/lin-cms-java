package com.lin.cms.demo.sleeve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
public class SpecKeyCreateOrUpdateDTO {
    @NotBlank(message = "规格键名不可为空")
    @Length(min = 1, max = 100, message = "规格键名长度不能超过100字符")
    private String name;

    @Length(min = 1, max = 255, message = "规格键描述长度不能超过255字符")
    private String description;

    @Length(min = 1, max = 30, message = "单位长度不能超过30字符")
    private String unit;

    @Positive(message = "标准值必须为正整数")
    private Integer standard;
}
