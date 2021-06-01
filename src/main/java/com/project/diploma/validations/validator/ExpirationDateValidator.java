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
        int year = Integer.parseInt(date.substring(3));
        if (year <= 21 || year >= 30){
            return false;
        } else {
            int month = Integer.parseInt(date.substring(0, 2));
            return (month < 6);
        }
    }
}
