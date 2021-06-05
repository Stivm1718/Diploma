package com.project.diploma.services.services.implementation;

import com.project.diploma.data.repositories.*;
import com.project.diploma.services.models.*;
import com.project.diploma.services.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final UserRepository userRepository;
    private final HeroRepository heroRepository;
    private final ItemRepository itemRepository;
    private final OfferRepository offerRepository;
    private final AchievementRepository achievementRepository;

    @Autowired
    public ValidationServiceImpl(UserRepository userRepository, HeroRepository heroRepository, ItemRepository itemRepository, OfferRepository offerRepository, AchievementRepository achievementRepository) {
        this.userRepository = userRepository;
        this.heroRepository = heroRepository;
        this.itemRepository = itemRepository;
        this.offerRepository = offerRepository;
        this.achievementRepository = achievementRepository;
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

    @Override
    public boolean isValidOfferName(CreateOfferServiceModel model) {
        return offerRepository.existsOfferByName(model.getName());
    }

    @Override
    public boolean isValidAchievementName(CreateAchievementServiceModel model) {
        return achievementRepository.existsByName(model.getName());
    }

    private boolean existUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
