package com.project.diploma.validations.validator;

import com.project.diploma.data.repositories.UserRepository;
import com.project.diploma.validations.EmailAvailabilityValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailAvailabilityValidator implements ConstraintValidator<EmailAvailabilityValidation, String> {

    private final UserRepository userRepository;

    @Autowired
    public EmailAvailabilityValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByEmail(email);
    }
}
