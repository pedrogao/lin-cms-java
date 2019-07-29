package com.lin.cms.demo.sleeve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.lin.cms.demo.validator.Length;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class BannerCreateOrUpdateDTO {
    @NotBlank(message = "banner名不可为空")
    @Length(min = 1, max = 100, allowBlank = true, message = "banner名长度不能超过100字符")
    private String name;

    @Length(min = 1, max = 255, allowBlank = true, message = "banner描述长度不能超过255字符")
    private String description;

}
