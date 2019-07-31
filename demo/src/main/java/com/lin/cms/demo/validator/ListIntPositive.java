package com.lin.cms.demo.validator;

import javax.validation.Payload;

public @interface ListIntPositive {

    String message() default "ListIntPositive cannot contain empty fields";

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    boolean allowBlank() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
