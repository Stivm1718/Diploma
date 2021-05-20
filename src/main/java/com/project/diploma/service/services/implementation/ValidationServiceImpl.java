package com.project.diploma.service.services.implementation;

import com.project.diploma.data.repository.HeroRepository;
import com.project.diploma.data.repository.ItemRepository;
import com.project.diploma.data.repository.UserRepository;
import com.project.diploma.service.models.CreateHeroServiceModel;
import com.project.diploma.service.models.CreateItemServiceModel;
import com.project.diploma.service.services.ValidationService;
import com.project.diploma.service.models.RegisterUserServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final UserRepository userRepository;
    private final HeroRepository heroRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ValidationServiceImpl(UserRepository userRepository, HeroRepository heroRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.heroRepository = heroRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public boolean isValidUser(RegisterUserServiceModel model) {
        return model.getPassword().equals(model.getConfirmPassword()) &&
                !existUsername(model.getUsername()) &&
                !existEmail(model.getEmail());
    }

    @Override
    public boolean isValidHeroName(CreateHeroServiceModel model) {
        return heroRepository.existsHeroByName(model.getName());
    }

    @Override
    public boolean isValidItemName(CreateItemServiceModel model) {
        return itemRepository.existsItemByName(model.getName());
    }

    private boolean existUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
