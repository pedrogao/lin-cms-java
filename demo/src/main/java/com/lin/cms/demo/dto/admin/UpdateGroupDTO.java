package com.lin.cms.demo.dto.admin;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateGroupDTO {
    @NotBlank(message = "请输入分组名称")
    @Length(min = 1, max = 60, message = "分组名称不可超过60字符")
    private String name;

    @Length(min = 1, max = 255, message = "分组名称不可超过255字符")
    private String info;
}
