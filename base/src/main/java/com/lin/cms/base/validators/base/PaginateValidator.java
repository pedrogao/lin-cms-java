package com.lin.cms.base.validators.base;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PaginateValidator {

    @Min(value = 1, message = "count必须为正整数")
    private Integer count;

    @Min(value = 0, message = "page必须为整数，且大于等于0")
    private Integer page;
}
