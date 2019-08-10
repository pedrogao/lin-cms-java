package com.lin.cms.demo.sleeve.dto;

import com.lin.cms.demo.validator.Length;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
public class GridCategoryCreateOrUpdateDTO {
    @Length(min = 1, max = 100, allowBlank = true, message = "标题长度不能超过255字符")
    private String title;

    @Length(min = 1, max = 100, allowBlank = true, message = "名称长度不能超过255字符")
    private String name;

    @NotBlank(message = "图片url不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "图片url长度不能超过255字符")
    private String img;

    @NotNull(message = "分类id不可为空")
    @Positive(message = "分类id必须为正整数")
    private Long categoryId;
}
