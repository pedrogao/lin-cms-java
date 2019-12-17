package com.lin.cms.demo.dto.admin;

import com.lin.cms.demo.common.validator.LongList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UpdateUserInfoDTO {

    @LongList(min = 1, allowBlank = true, message = "分组id必须是整数，且大于0")
    private List<Long> groupIds;
}
