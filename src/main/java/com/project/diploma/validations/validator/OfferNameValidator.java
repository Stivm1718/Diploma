package com.project.diploma.validations.validator;

import com.project.diploma.validations.OfferNameValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OfferNameValidator implements ConstraintValidator<OfferNameValidation, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name.matches("^[A-Z]([a-zA-Z ]{4,29})$");
    }
}
