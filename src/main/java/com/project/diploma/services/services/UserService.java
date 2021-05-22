package com.project.diploma.services.services;

import com.project.diploma.services.models.LoginUserServiceModel;
import com.project.diploma.services.models.RegisterUserServiceModel;
import com.project.diploma.web.models.LoggedUserFilterModel;
import com.project.diploma.web.models.ProfileUserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean register(RegisterUserServiceModel model);

    boolean login(LoginUserServiceModel user);

    LoggedUserFilterModel findLogUser(String username);

    ProfileUserModel getDetailsForUser(String username);
}
