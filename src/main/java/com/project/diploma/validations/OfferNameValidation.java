package com.project.diploma.validations;

import com.project.diploma.validations.validator.OfferNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = OfferNameValidator.class)
public @interface OfferNameValidation {

    String message() default "The name must start with a capital letter and have 5 to 30 characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
