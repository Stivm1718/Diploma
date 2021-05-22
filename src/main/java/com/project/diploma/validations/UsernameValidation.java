package com.project.diploma.validations;

import com.project.diploma.validations.validator.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UsernameValidator.class)
public @interface UsernameValidation {

    String message() default "The length of the name must be between 3 and 25 " +
            "characters, and can only contain lowercase and uppercase letters, numbers, " +
            "dashes, and underscores.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
