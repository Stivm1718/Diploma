package com.project.diploma.validation.validator;

import com.project.diploma.validation.EqualsPasswordValidation;
import com.project.diploma.web.models.RegisterUserModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualsPasswordValidator implements ConstraintValidator<EqualsPasswordValidation, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        RegisterUserModel model = (RegisterUserModel) value;
        return model.getPassword().equals(model.getConfirmPassword());
    }
}
