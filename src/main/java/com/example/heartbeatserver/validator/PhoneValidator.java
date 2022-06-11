package com.example.heartbeatserver.validator;

import com.example.heartbeatserver.annotations.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String pattern = "^(\\+?0?86\\-?)?1[345789]\\d{9}$|^(\\+?0?852\\-?)([6|9])\\d{7}$"; // 大陆&香港手机号
        return Pattern.matches(pattern, o.toString());
    }
}
