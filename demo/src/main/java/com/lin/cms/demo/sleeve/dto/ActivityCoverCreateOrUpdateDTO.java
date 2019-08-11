package com.lin.cms.demo.sleeve.dto;

import com.lin.cms.demo.sleeve.enums.OnlineOrNot;
import com.lin.cms.demo.validator.Enum;
import com.lin.cms.demo.validator.Length;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class ActivityCoverCreateOrUpdateDTO {

    @NotBlank(message = "活动标题不可为空")
    @Length(min = 1, max = 60, allowBlank = true, message = "活动标题长度不能超过60字符")
    private String title;

    @NotBlank(message = "活动名不可为空")
    @Length(min = 1, max = 20, allowBlank = true, message = "活动名长度不能超过20字符")
    private String name;

    @Length(min = 1, max = 255, allowBlank = true, message = "活动描述长度不能超过255字符")
    private String description;

    @Enum(allowNull = false, target = OnlineOrNot.class, message = "上线值必须为0,1中的一种")
    private Integer online;

    @NotBlank(message = "主图url不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "入口图url长度不能超过255字符")
    private String coverImg;

    @NotBlank(message = "顶部图url不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "顶部图url长度不能超过255字符")
    private String internalTopImg;
}
