package com.example.wsb.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegisterRequestValidator.class)
public @interface CheckRegisterRequest {
    String message() default "MISSING_REQUIRED_PARAMETERS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
