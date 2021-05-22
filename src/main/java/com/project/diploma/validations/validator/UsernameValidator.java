package com.project.diploma.validations.validator;

import com.project.diploma.validations.UsernameValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameValidation, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name.matches("^[a-zA-Z0-9_-]{3,25}$");
    }
}
