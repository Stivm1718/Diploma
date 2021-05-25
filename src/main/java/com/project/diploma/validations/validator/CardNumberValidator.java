package com.project.diploma.validations.validator;

import com.project.diploma.validations.CardNumberValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardNumberValidator implements ConstraintValidator<CardNumberValidation, String> {
    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        return number.matches("^[0-9]{16}$");
    }
}
