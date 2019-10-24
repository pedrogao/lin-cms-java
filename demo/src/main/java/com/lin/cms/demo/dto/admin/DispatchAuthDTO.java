package com.lin.cms.demo.dto.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class DispatchAuthDTO {

    @Min(value = 1, message = "分组id必须正整数")
    @NotNull(message = "分组id不可为空")
    private Long groupId;

    @NotBlank
    private String auth;
}
