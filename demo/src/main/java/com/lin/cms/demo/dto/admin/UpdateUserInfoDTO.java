package com.lin.cms.demo.dto.admin;

import com.lin.cms.validator.LongList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UpdateUserInfoDTO {

    @LongList(min = 1, allowBlank = true, message = "{group.ids.long-list}")
    private List<Long> groupIds;
}
