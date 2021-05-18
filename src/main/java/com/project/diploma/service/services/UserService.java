package com.project.diploma.service.services;

import com.project.diploma.service.models.LoginUserServiceModel;
import com.project.diploma.service.models.RegisterUserServiceModel;
import com.project.diploma.web.models.LoggedUserFilterModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean register(RegisterUserServiceModel model);

    boolean login(LoginUserServiceModel user);

    LoggedUserFilterModel findLogUser(String username);
}
