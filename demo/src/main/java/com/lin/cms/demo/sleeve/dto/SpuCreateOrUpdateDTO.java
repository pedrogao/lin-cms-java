package com.lin.cms.demo.sleeve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
public class SpuCreateOrUpdateDTO {
    @NotBlank(message = "规格值名不可为空")
    @Length(min = 1, max = 255, message = "规格值名长度不能超过255字符")
    private String title;

    @NotBlank(message = "副标题不可为空")
    @Length(min = 1, max = 255, message = "规格值扩展描述长度不能超过255字符")
    private String subtitle;

    @NotBlank(message = "图片url不可为空")
    @Length(min = 1, max = 255, message = "图片url长度不能超过255字符")
    private String img;

    @Positive(message = "分类id必须为正整数")
    @NotNull(message = "分类id不可为空")
    private Long categoryId;


    @Positive(message = "品牌id必须为正整数")
    @NotNull(message = "品牌id不可为空")
    private Long brandId;


    @Positive(message = "默认规格键id必须为正整数")
    @NotNull(message = "默认规格键id不可为空")
    private Long sketchSpecId;


    @Positive(message = "默认 sku id 必须为正整数")
    @NotNull(message = "默认 sku id不可为空")
    private Long defaultSkuId;

    @Length(min = 1, max = 60, message = "价格长度不能超过60字符")
    private String price;

}
