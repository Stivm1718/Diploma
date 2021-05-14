package com.project.diploma.validation.validator;

import com.project.diploma.validation.PasswordValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.matches("^(?=.{6,15})(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&.+*!?=]).*$");
    }
}
