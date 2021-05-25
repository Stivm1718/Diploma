package com.project.diploma.validations;

import com.project.diploma.validations.validator.OfferNameAvailabilityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = OfferNameAvailabilityValidator.class)
public @interface OfferNameAvailabilityValidation {

    String message() default "The name of offer is unavailable.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
