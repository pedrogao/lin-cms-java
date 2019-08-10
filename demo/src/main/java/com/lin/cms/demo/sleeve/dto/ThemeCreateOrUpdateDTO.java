package com.lin.cms.demo.sleeve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.lin.cms.demo.validator.Length;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class ThemeCreateOrUpdateDTO {
    @NotBlank(message = "主题名不可为空")
    @Length(min = 1, max = 60, allowBlank = true, message = "主题名长度不能超过60字符")
    private String title;

    @Length(min = 1, max = 255, allowBlank = true, message = "主题描述长度不能超过255字符")
    private String description;

    @NotBlank(message = "主题位置不可为空")
    @Length(min = 1, max = 30, allowBlank = true, message = "主题位置长度不能超过30字符")
    private String location;

    @NotBlank(message = "主图不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "主图长度不能超过255字符")
    private String img;

    @Length(min = 1, max = 30, allowBlank = true, message = "主题扩展长度不能超过255字符")
    private String extend;

    @Length(min = 1, max = 255, allowBlank = true, message = "入口图长度不能超过255字符")
    private String entranceImg;

    @Length(min = 1, max = 255, allowBlank = true, message = "外链图长度不能超过255字符")
    private String internalTopImg;
}
