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
    @NotBlank(message = "名称不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "名称长度不能超过255字符")
    private String name;

    @Length(min = 1, max = 255, allowBlank = true, message = "标题长度不能超过255字符")
    private String title;

    @Length(min = 1, max = 255, allowBlank = true, message = "图片url长度不能超过255字符")
    private String img;

    @Length(min = 1, max = 255, allowBlank = true, message = "描述长度不能超过255字符")
    private String description;

}
