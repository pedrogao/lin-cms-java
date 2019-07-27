package com.lin.cms.demo.sleeve.dto;

import com.lin.cms.demo.sleeve.enums.Currency;
import com.lin.cms.demo.sleeve.enums.SaleOrNot;
import com.lin.cms.demo.validator.Enum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SkuCreateOrUpdateDTO {
    @NotBlank(message = "规格值名不可为空")
    @Length(min = 1, max = 255, message = "规格值名长度不能超过255字符")
    private String title;

    @NotBlank(message = "图片url不可为空")
    @Length(min = 1, max = 255, message = "图片url长度不能超过255字符")
    private String img;

    @DecimalMin(value = "0.00", message = "折扣不能为负")
    private BigDecimal discount;

    @Enum(target = SaleOrNot.class, allowNull = true, message = "是否出售必须在0，1中选择")
    private Integer onSale;

    @Positive(message = "spu id 必须为正整数")
    @NotNull(message = "spu id不可为空")
    private Long spuId;

    @NotNull(message = "价格不可为空")
    @DecimalMin(value = "0.00", message = "价格不能为负")
    private BigDecimal price;

    @Enum(target = Currency.class, allowNull = true, message = "货币目前必须为CNY")
    private String currency;

    @Positive(message = "库存必须为正整数")
    private Integer stock;

    // 规则参数列表
    // [
    // {"key": "颜色", "value": "深白色", "key_id": 1, "value_id": 3},
    // {"key": "图案", "value": "灌篮高手", "key_id": 3, "value_id": 10},
    // {"key": "尺码", "value": "中号", "key_id": 4, "value_id": 15}
    // ]
    // 2#1-3$3-10$4-15
    @Valid
    @NotNull(message = "sku选择器不可为空")
    private List<SkuSelector> selectors;
}
