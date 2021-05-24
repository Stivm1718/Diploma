package com.project.diploma.validations.validator;

import com.project.diploma.validations.MoneyValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MoneyValidator implements ConstraintValidator<MoneyValidation, Double> {
    @Override
    public boolean isValid(Double price, ConstraintValidatorContext constraintValidatorContext) {
        return price >= 0.1;
    }
}
