package com.lin.cms.demo.common.validator;

import javax.validation.Payload;

/**
 * 整型列表校验，校验List<Long> 类型
 */
public @interface LongList {

    String message() default "LongList cannot contain empty fields";

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    boolean allowBlank() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
