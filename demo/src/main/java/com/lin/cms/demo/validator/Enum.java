package com.lin.cms.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValidator.class})
@Documented
public @interface Enum {
    String message() default "";

    boolean allowNull() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> target();
}
