package com.example.heartbeatserver.annotations;

import com.example.heartbeatserver.validator.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    String message() default "手机号格式有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
