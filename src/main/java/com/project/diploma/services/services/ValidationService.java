package com.project.diploma.services.services;

import com.project.diploma.services.models.*;

public interface ValidationService {

    boolean isValidUser(RegisterUserServiceModel model);

    boolean isValidHeroName(CreateHeroServiceModel model);

    boolean isValidItemName(CreateItemServiceModel model);

    boolean isValidOfferName(CreateOfferServiceModel model);

    boolean isValidAchievementName(CreateAchievementServiceModel model);
}
