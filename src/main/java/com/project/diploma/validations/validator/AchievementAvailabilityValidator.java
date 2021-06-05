package com.project.diploma.validations.validator;

import com.project.diploma.data.repositories.AchievementRepository;
import com.project.diploma.validations.AchievementAvailabilityValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AchievementAvailabilityValidator implements ConstraintValidator<AchievementAvailabilityValidation, String> {

    private final AchievementRepository achievementRepository;

    @Autowired
    public AchievementAvailabilityValidator(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !achievementRepository.existsByName(name);
    }
}
