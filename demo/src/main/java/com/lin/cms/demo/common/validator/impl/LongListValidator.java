package com.lin.cms.demo.common.validator.impl;

import com.lin.cms.demo.common.validator.LongList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class LongListValidator implements ConstraintValidator<LongList, List<Long>> {

    private int min;

    private int max;

    private boolean allowBlank;

    @Override
    public void initialize(LongList constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
        this.allowBlank = constraintAnnotation.allowBlank();
    }

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {
        if (value.isEmpty() && this.allowBlank) {
            return true;
        }
        if (value.size() < min || value.size() > max) {
            return false;
        }
        for (int i = 0; i < value.size(); i++) {
            Long o = value.get(i);
            if (o < 0) {
                return false;
            }
        }
        return true;
    }
}
