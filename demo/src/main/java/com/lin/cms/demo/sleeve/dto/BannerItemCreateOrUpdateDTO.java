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
public class BannerItemCreateOrUpdateDTO {

    @NotBlank(message = "图片url不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "图片url长度不能超过255字符")
    private String img;

    @Length(min = 1, max = 255, allowBlank = true, message = "名称长度不能超过255字符")
    private String name;

    @NotNull(message = "类型不可为空")
    @Positive(message = "类型必须为正整数")
    private Integer type;

    @NotNull(message = "父banner id 不可为空")
    @Positive(message = "父banner id 必须为正整数")
    private Long bannerId;

    @Length(min = 1, max = 50, allowBlank = true, message = "关键字长度不能超过50字符")
    private String keyword;

}
