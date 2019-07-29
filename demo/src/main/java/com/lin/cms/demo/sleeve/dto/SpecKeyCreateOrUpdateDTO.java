package com.lin.cms.demo.sleeve.dto;

import com.lin.cms.demo.sleeve.enums.StandardOrNot;
import com.lin.cms.demo.validator.Enum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.lin.cms.demo.validator.Length;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class SpecKeyCreateOrUpdateDTO {
    @NotBlank(message = "规格键名不可为空")
    @Length(min = 1, max = 100, allowBlank = true, message = "规格键名长度不能超过100字符")
    private String name;

    @Length(min = 1, max = 255, allowBlank = true, message = "规格键描述长度不能超过255字符")
    private String description;

    @Length(min = 1, max = 30, allowBlank = true, message = "单位不能超过30字符")
    private String unit;

    @Enum(target = StandardOrNot.class, allowNull = true, message = "标准值必须为0，1中的一种")
    private Integer standard;
}
