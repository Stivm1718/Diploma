package com.project.diploma.validations;

import com.project.diploma.validations.validator.NumberValidator;

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

    String message() default "Тhe number must be positive.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
