package com.lin.cms.demo.dto.admin;

import com.lin.cms.validator.Length;
import lombok.Data;

@Data
public class UpdateGroupDTO {

    @Length(min = 1, max = 60, message = "{group.name.length}")
    private String name;

    @Length(min = 1, max = 255, message = "{group.info.length}")
    private String info;
}
