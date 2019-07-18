package com.lin.cms.demo.dto.admin;

import com.lin.cms.demo.validator.NotEmptyFields;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class DispatchAuthsDTO {
    @Min(value = 1, message = "分组id必须正整数")
    @NotNull(message = "分组id不可为空")
    private Integer groupId;

    @NotEmptyFields(message = "请输入auths字段，且每一项不可为空")
    private List<String> auths;
}
