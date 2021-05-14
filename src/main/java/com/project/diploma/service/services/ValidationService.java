package com.project.diploma.service.services;

import com.project.diploma.service.models.RegisterUserServiceModel;

public interface ValidationService {

    boolean isValid(RegisterUserServiceModel model);
}
