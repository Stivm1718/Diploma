package com.project.diploma.validations.validator;


import com.project.diploma.data.repositories.OfferRepository;
import com.project.diploma.validations.OfferNameAvailabilityValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OfferNameAvailabilityValidator implements ConstraintValidator<OfferNameAvailabilityValidation, String> {

    private final OfferRepository offerRepository;

    @Autowired
    public OfferNameAvailabilityValidator(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !offerRepository.existsOfferByName(name);
    }
}
