package com.project.diploma.validations.validator;

import com.project.diploma.validations.NumberValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<NumberValidation, Integer> {
    @Override
    public boolean isValid(Integer number, ConstraintValidatorContext constraintValidatorContext) {
        return number > 0;
    }
}
