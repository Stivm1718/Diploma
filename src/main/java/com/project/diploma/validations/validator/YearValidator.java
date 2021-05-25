package com.project.diploma.validations.validator;

import com.project.diploma.validations.YearValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<YearValidation, Integer> {
    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext constraintValidatorContext) {
        return year >= 23 && year <= 30;
    }
}
