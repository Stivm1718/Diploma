package com.project.diploma.validations.validator;

import com.project.diploma.validations.CardholderValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardholderValidator implements ConstraintValidator<CardholderValidation, String> {
    @Override
    public boolean isValid(String cardholder, ConstraintValidatorContext constraintValidatorContext) {
        return cardholder.matches("^[A-Za-z ]{3,}$");
    }
}
