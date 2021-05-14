package com.project.diploma.validation;

import com.project.diploma.validation.validator.NumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NumberValidator.class)
public @interface NumberValidation {

    String message() default "Name could be between 1 and 100";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
