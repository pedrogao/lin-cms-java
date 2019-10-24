package com.lin.cms.demo.dto.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class UpdateGroupDTO {
    @NotBlank(message = "请输入分组名称")
    private String name;

    private String info;
}
