package com.project.diploma.validations.validator;

import com.project.diploma.data.repositories.HeroRepository;
import com.project.diploma.validations.HeroNameAvailabilityValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HeroNameAvailabilityValidator implements ConstraintValidator<HeroNameAvailabilityValidation, String> {

    private final HeroRepository heroRepository;

    @Autowired
    public HeroNameAvailabilityValidator(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !heroRepository.existsHeroByName(name);
    }
}
