package com.lin.cms.demo.validator;

import javax.validation.Payload;

public @interface DateTime {
    String message() default "日期格式错误";

    String format() default "yyyy-MM-dd HH:mm:ss";

    boolean allowNull() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
