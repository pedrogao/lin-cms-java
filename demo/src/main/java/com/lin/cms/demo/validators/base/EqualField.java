package com.lin.cms.demo.validators.base;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({TYPE, METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EqualFieldValidator.class})// 与约束注解关联的验证器
public @interface EqualField {
    String srcField() default "";

    String dstField() default "";

    String message() default "两者必须保证一致";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
