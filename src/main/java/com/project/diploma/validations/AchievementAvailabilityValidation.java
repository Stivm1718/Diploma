package com.project.diploma.validations;

import com.project.diploma.validations.validator.AchievementAvailabilityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AchievementAvailabilityValidator.class)
public @interface AchievementAvailabilityValidation {

    String message() default "Achievement is unavailable.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
