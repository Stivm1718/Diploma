package com.project.diploma.validations;

import com.project.diploma.validations.validator.AchievementValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AchievementValidator.class)
public @interface AchievementValidation {

    String message() default "The achievement must start with a capital letter, " +
            "can contain a space, a dash, a underscore or digits, " +
            "and must contain between 5 and 30 characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
