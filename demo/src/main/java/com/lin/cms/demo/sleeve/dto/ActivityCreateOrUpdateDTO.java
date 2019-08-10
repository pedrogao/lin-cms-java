package com.lin.cms.demo.sleeve.dto;

import com.lin.cms.demo.sleeve.enums.OnlineOrNot;
import com.lin.cms.demo.validator.DateTime;
import com.lin.cms.demo.validator.Enum;
import com.lin.cms.demo.validator.Length;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class ActivityCreateOrUpdateDTO {

    @NotBlank(message = "活动标题不可为空")
    @Length(min = 1, max = 60, allowBlank = true, message = "活动标题长度不能超过60字符")
    private String title;

    @NotBlank(message = "活动名不可为空")
    @Length(min = 1, max = 20, allowBlank = true, message = "活动名长度不能超过20字符")
    private String name;

    @Length(min = 1, max = 255, allowBlank = true, message = "活动描述长度不能超过255字符")
    private String description;

    @DateTime(allowNull = false, message = "开始时间不可为空，且格式必须为 yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTime(allowNull = false, message = "结束时间不可为空，且格式必须为 yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @Length(min = 1, max = 60, allowBlank = true, message = "活动信息长度不能超过60字符")
    private String remark;

    @Enum(allowNull = false, target = OnlineOrNot.class, message = "上线值必须为0,1中的一种")
    private Integer online;

    @Positive(message = "活动页id必须为正整数")
    @NotNull(message = "活动页不可为空")
    private Long activityCoverId;

    @NotBlank(message = "入口图url不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "入口图url长度不能超过255字符")
    private String entranceImg;

    @NotBlank(message = "顶部图url不可为空")
    @Length(min = 1, max = 255, allowBlank = true, message = "顶部图url长度不能超过255字符")
    private String internalTopImg;

}
