package com.lin.cms.demo.dto.admin;

import com.lin.cms.validator.LongList;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class RemovePermissionsDTO {

    @Positive(message = "{group.id.positive}")
    @NotNull(message = "{group.id.not-null}")
    private Long groupId;

    @LongList(allowBlank = true, message = "{permission.ids.long-list}")
    private List<Long> permissionIds;
}
