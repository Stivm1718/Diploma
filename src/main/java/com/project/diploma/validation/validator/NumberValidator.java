package com.project.diploma.validation.validator;

import com.project.diploma.validation.NumberValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<NumberValidation, Integer> {
    @Override
    public boolean isValid(Integer number, ConstraintValidatorContext constraintValidatorContext) {
        return number > 0 && number < 101;
    }
}
