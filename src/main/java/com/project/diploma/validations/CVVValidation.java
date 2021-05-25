package com.project.diploma.validations;

import com.project.diploma.validations.validator.CVVValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CVVValidator.class)
public @interface CVVValidation {

    String message() default "Invalid cvv.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
