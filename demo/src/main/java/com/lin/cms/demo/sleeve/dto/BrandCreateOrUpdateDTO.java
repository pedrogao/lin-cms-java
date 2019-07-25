package com.lin.cms.demo.sleeve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class BrandCreateOrUpdateDTO {
    @NotBlank(message = "品牌名不可为空")
    @Length(min = 1, max = 100, message = "品牌名长度不能超过100字符")
    private String name;

    @Length(min = 1, max = 255, message = "品牌描述长度不能超过255字符")
    private String description;
}
