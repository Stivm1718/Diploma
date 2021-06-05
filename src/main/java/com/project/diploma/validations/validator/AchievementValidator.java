package com.project.diploma.validations.validator;

import com.project.diploma.validations.AchievementValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AchievementValidator implements ConstraintValidator<AchievementValidation, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name.matches("^[A-Z]([a-zA-Z0-9 ]{4,29})$");
    }
}
