package com.project.diploma.validation;

import com.project.diploma.validation.validator.EqualsPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = EqualsPasswordValidator.class)
public @interface EqualsPasswordValidation {
    String message() default "Passwords are not equal.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
