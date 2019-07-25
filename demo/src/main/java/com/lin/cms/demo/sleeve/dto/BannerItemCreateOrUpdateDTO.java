package com.lin.cms.demo.sleeve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
public class BannerItemCreateOrUpdateDTO {

    @NotNull(message = "图片id不可为空")
    @Positive(message = "图片id必须为正整数")
    private Integer imgId;

    @NotNull(message = "类型不可为空")
    @Positive(message = "类型必须为正整数")
    private Integer type;

    @NotNull(message = "父banner id 不可为空")
    @Positive(message = "父banner id 必须为正整数")
    private Integer bannerId;

    @Length(min = 1, max = 50, message = "关键字长度不能超过50字符")
    private String keyWord;

}
