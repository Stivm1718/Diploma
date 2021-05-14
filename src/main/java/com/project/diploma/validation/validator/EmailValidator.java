package com.project.diploma.validation.validator;

import com.project.diploma.validation.EmailValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email.matches("^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
