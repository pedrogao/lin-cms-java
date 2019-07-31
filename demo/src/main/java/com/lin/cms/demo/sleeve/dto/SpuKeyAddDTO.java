package com.lin.cms.demo.sleeve.dto;

import com.lin.cms.demo.validator.ListIntPositive;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class SpuKeyAddDTO {

    @Positive(message = "spuId必须为正整数")
    @NotNull(message = "spuId不可为空")
    private Long spuId;


    @ListIntPositive(min = 1, message = "规格值列表不可为空，且每一项必须为正整数")
    @NotNull(message = "规格值列表不可为空")
    private List<Long> specKeyIds;
}
