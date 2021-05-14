package com.project.diploma.validation;

import com.project.diploma.validation.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValidation {

    String message() default "Password must be between 6 and 15 symbols and " +
            "must contain at least one capital letter, lowercase letter, number " +
            "and special character.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
