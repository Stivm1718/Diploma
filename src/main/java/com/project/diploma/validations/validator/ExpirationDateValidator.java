package com.project.diploma.validations.validator;

import com.project.diploma.validations.ExpirationDateValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExpirationDateValidator implements ConstraintValidator<ExpirationDateValidation, String> {

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        if (date.equals("")){
            return false;
        }
        int month = Integer.parseInt(date.substring(0, 2));
        if (month < 6){
            return false;
        } else {
            int year = Integer.parseInt(date.substring(3));
            return (year >= 21 && year <= 30);
        }
    }
}
