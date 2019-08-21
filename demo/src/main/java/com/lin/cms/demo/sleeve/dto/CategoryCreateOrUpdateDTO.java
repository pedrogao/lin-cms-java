package com.lin.cms.demo.sleeve.dto;

import com.lin.cms.demo.sleeve.enums.CategoryRootOrNot;
import com.lin.cms.demo.sleeve.enums.OnlineOrNot;
import com.lin.cms.demo.validator.Enum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.lin.cms.demo.validator.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
public class CategoryCreateOrUpdateDTO {

    @NotBlank(message = "种类名不可为空")
    @Length(min = 1, max = 100, allowBlank = true, message = "种类名长度不能超过100字符")
    private String name;

    @Length(min = 1, max = 255, allowBlank = true, message = "种类描述长度不能超过255字符")
    private String description;

    @Enum(message = "种类root必须为0，1中的一项", allowNull = true, target = CategoryRootOrNot.class)
    private Integer isRoot;

    @Positive(message = "父种类id必须为正整数")
    private Long parentId;

    @Length(min = 1, max = 255, allowBlank = true, message = "图片url长度不能超过100字符")
    private String img;

    @Positive(message = "排序必须为正整数")
    private Integer index;

    @Enum(message = "online必须为0，1中的一项", allowNull = true, target = OnlineOrNot.class)
    private Integer online;

    @Positive(message = "等级必须为正整数")
    private Integer level;
}
