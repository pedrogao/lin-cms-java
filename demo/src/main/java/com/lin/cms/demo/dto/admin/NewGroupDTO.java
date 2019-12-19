package com.lin.cms.demo.dto.admin;

import com.lin.cms.demo.common.validator.LongList;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class NewGroupDTO {
    @NotBlank(message = "{group.name.not-blank}")
    @Length(min = 1, max = 60, message = "{group.name.length}")
    private String name;

    @Length(min = 1, max = 255, message = "{group.info.length}")
    private String info;

    @LongList(allowBlank = true, message = "{permission.ids.long-list}")
    private List<Long> permissionIds;
}
