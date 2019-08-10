package com.lin.cms.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

public class DateTimeValidator implements ConstraintValidator<DateTime, String> {

    private DateTime dateTime;

    @Override
    public void initialize(DateTime dateTime) {
        this.dateTime = dateTime;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果value为null
        if (value == null) {
            return dateTime.allowNull();
        } else {
            String format = dateTime.format();
            if (value.length() != format.length()) {
                return false;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            try {
                simpleDateFormat.parse(value);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }
}
