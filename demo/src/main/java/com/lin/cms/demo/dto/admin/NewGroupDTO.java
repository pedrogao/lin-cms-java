package com.lin.cms.demo.dto.admin;

import com.lin.cms.demo.common.validator.NotEmptyFields;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class NewGroupDTO {
    @NotBlank(message = "请输入分组名称")
    private String name;

    private String info;

    @NotEmptyFields(message = "请输入权限字段，且每一项不可为空")
    private List<String> permissions;
}
