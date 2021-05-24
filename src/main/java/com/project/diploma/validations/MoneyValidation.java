package com.project.diploma.validations;

import com.project.diploma.validations.validator.MoneyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = MoneyValidator.class)
public @interface MoneyValidation {

    String message() default "Тhe price cannot be less than BGN 0.1";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
