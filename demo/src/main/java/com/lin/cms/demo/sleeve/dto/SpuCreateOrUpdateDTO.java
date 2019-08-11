package com.lin.cms.demo.sleeve.dto;

import com.lin.cms.demo.sleeve.enums.SaleOrNot;
import com.lin.cms.demo.validator.Enum;
import com.lin.cms.demo.validator.ListIntPositive;
import com.lin.cms.demo.validator.NotEmptyFields;
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

    @Length(min = 1, max = 255, allowBlank = true, message = "主题图url长度不能超过255字符")
    private String forThemeImg;

    @Positive(message = "分类id必须为正整数")
    @NotNull(message = "分类id不可为空")
    private Long categoryId;

    @Enum(target = SaleOrNot.class, allowNull = true, message = "是否出售必须在0，1中选择")
    private Integer onSale;

    @Enum(target = SaleOrNot.class, allowNull = true, message = "是否测试必须在0，1中选择")
    private Integer isTest;

    @Positive(message = "品牌id必须为正整数")
    @NotNull(message = "品牌id不可为空")
    private Long brandId;

    @Positive(message = "默认规格键id必须为正整数")
    @NotNull(message = "默认规格键id不可为空")
    private Long sketchSpecId;

    @Positive(message = "默认 sku id 必须为正整数")
    private Long defaultSkuId;

    @Length(min = 1, max = 20, allowBlank = true, message = "价格长度不能超过20字符")
    private String price;

    @Length(min = 1, max = 20, allowBlank = true, message = "折扣长度不能超过20字符")
    private String discountPrice;

    @Length(min = 1, max = 255, allowBlank = true, message = "描述长度不能超过255字符")
    private String description;

    @Length(min = 1, max = 255, allowBlank = true, message = "标签长度不能超过255字符")
    private String tags;

    @ListIntPositive(min = 1, allowBlank = true, message = "规格值列表每一项必须为正整数")
    private List<Long> specKeyIds;

    @NotEmptyFields(message = "请输入轮播图图片，且每一项不可为空", allowNull = true, allowEmpty = true)
    private List<String> bannerImgs;

    @NotEmptyFields(message = "请输入详情图图片，且每一项不可为空", allowNull = true, allowEmpty = true)
    private List<String> detailImgs;
}
