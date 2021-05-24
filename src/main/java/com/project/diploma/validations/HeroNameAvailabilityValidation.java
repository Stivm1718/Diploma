package com.project.diploma.validations;

import com.project.diploma.validations.validator.HeroNameAvailabilityValidator;
import com.project.diploma.validations.validator.UsernameAvailabilityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = HeroNameAvailabilityValidator.class)
public @interface HeroNameAvailabilityValidation {

    String message() default "The name of hero is unavailable.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
