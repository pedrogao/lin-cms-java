package com.lin.cms.demo.sleeve.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ThemeSpuCreateDTO {

    @Positive(message = "主题id必须为正整数")
    @NotNull(message = "主题id不可为空")
    private Long themeId;

    @Positive(message = "spu id必须为正整数")
    @NotNull(message = "spu id不可为空")
    private Long spuId;
}
