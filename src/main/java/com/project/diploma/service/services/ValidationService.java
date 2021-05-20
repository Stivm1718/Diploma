package com.project.diploma.service.services;

import com.project.diploma.service.models.CreateHeroServiceModel;
import com.project.diploma.service.models.CreateItemServiceModel;
import com.project.diploma.service.models.RegisterUserServiceModel;

public interface ValidationService {

    boolean isValidUser(RegisterUserServiceModel model);

    boolean isValidHeroName(CreateHeroServiceModel model);

    boolean isValidItemName(CreateItemServiceModel model);
}
