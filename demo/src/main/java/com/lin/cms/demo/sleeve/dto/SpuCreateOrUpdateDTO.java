package com.lin.cms.demo.sleeve.dto;

import com.lin.cms.demo.validator.ListIntPositive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.lin.cms.demo.validator.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SpuCreateOrUpdateDTO {
    @NotBlank(message = "标题不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "规格值名长度不能超过255字符")
    private String title;

    @NotBlank(message = "副标题不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "规格值扩展描述长度不能超过255字符")
    private String subtitle;

    @NotBlank(message = "图片url不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "图片url长度不能超过255字符")
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
    private Long defaultSkuId;

    @Length(min = 1, max = 60, allowBlank = true, message = "价格长度不能超过60字符")
    private String price;

    @Length(min = 1, max = 255, allowBlank = true, message = "标签长度不能超过255字符")
    private String tags;

    @ListIntPositive(min = 1, allowBlank = true, message = "规格值列表每一项必须为正整数")
    private List<Long> specKeyIds;
}
