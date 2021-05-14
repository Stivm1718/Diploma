package com.project.diploma.service.services.implementation;

import com.project.diploma.data.repository.UserRepository;
import com.project.diploma.service.services.ValidationService;
import com.project.diploma.service.models.RegisterUserServiceModel;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final UserRepository userRepository;

    public ValidationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(RegisterUserServiceModel model) {
        return model.getPassword().equals(model.getConfirmPassword()) &&
                !existUsername(model.getUsername()) &&
                !existEmail(model.getEmail());
    }

    private boolean existUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
