package com.project.diploma.validation.validator;

import com.project.diploma.data.repository.UserRepository;
import com.project.diploma.validation.UsernameAvailabilityValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameAvailabilityValidator implements ConstraintValidator<UsernameAvailabilityValidation, String> {

    private final UserRepository userRepository;

    @Autowired
    public UsernameAvailabilityValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByUsername(username);
    }
}
