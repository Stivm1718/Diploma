package com.project.diploma.validations.validator;

import com.project.diploma.validations.MonthValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MonthValidator implements ConstraintValidator<MonthValidation, Integer> {
    @Override
    public boolean isValid(Integer month, ConstraintValidatorContext constraintValidatorContext) {
        return month >= 1 && month <= 12;
    }
}
