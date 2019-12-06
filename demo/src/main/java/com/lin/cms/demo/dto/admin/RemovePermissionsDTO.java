package com.lin.cms.demo.dto.admin;

import com.lin.cms.demo.common.validator.NotEmptyFields;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RemovePermissionsDTO {
    @Min(value = 1, message = "分组id必须正整数")
    @NotNull(message = "分组id不可为空")
    private Long groupId;

    @NotEmptyFields(message = "请输入auths字段，且每一项不可为空")
    private List<Long> permissionIds;
}
