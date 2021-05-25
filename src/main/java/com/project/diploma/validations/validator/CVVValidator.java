package com.project.diploma.validations.validator;

import com.project.diploma.validations.CVVValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CVVValidator implements ConstraintValidator<CVVValidation, Integer> {
    @Override
    public boolean isValid(Integer cvv, ConstraintValidatorContext constraintValidatorContext) {
        return cvv >= 100 && cvv <= 999;
    }
}
