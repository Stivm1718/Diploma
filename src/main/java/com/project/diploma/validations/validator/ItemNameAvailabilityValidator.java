package com.project.diploma.validations.validator;

import com.project.diploma.data.repositories.ItemRepository;
import com.project.diploma.validations.ItemNameAvailabilityValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ItemNameAvailabilityValidator implements ConstraintValidator<ItemNameAvailabilityValidation, String> {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemNameAvailabilityValidator(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !itemRepository.existsItemByName(name);
    }
}
