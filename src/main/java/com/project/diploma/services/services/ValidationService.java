package com.project.diploma.services.services;

import com.project.diploma.services.models.CreateHeroServiceModel;
import com.project.diploma.services.models.CreateItemServiceModel;
import com.project.diploma.services.models.CreateOfferServiceModel;
import com.project.diploma.services.models.RegisterUserServiceModel;

public interface ValidationService {

    boolean isValidUser(RegisterUserServiceModel model);

    boolean isValidHeroName(CreateHeroServiceModel model);

    boolean isValidItemName(CreateItemServiceModel model);

    boolean isValidOfferName(CreateOfferServiceModel model);
}
