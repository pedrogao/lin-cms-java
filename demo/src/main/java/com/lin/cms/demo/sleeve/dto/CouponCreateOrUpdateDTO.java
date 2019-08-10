package com.lin.cms.demo.sleeve.dto;

import com.lin.cms.demo.sleeve.enums.CouponType;
import com.lin.cms.demo.validator.DateTime;
import com.lin.cms.demo.validator.Enum;
import com.lin.cms.demo.validator.Length;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class CouponCreateOrUpdateDTO {
    @NotBlank(message = "优惠卷标题不可为空")
    @Length(min = 1, max = 100, allowBlank = true, message = "优惠卷标题长度不能超过255字符")
    private String title;

    @DateTime(allowNull = false, message = "开始时间不可为空，且格式必须为 yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTime(allowNull = false, message = "结束时间不可为空，且格式必须为 yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @Length(min = 1, max = 255, allowBlank = true, message = "优惠卷描述长度不能超过255字符")
    private String description;

    @DecimalMin(value = "0.00", message = "满减额不能为负")
    private BigDecimal fullMoney;

    @DecimalMin(value = "0.00", message = "优惠额不能为负")
    private BigDecimal minus;

    /**
     * 国内多是打折，国外多是百分比 off
     */
    @DecimalMin(value = "0.00", message = "折扣不能为负")
    private BigDecimal discount;

    /**
     * 1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券
     */
    @Enum(allowNull = false, target = CouponType.class, message = "优惠卷类型必须为1,2,3,4中的一种")
    private Integer type;
}
