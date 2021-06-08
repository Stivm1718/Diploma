package com.project.diploma.services.services.implementation;

import com.project.diploma.data.models.Achievement;
import com.project.diploma.data.models.User;
import com.project.diploma.data.repositories.AchievementRepository;
import com.project.diploma.data.repositories.UserRepository;
import com.project.diploma.services.models.CreateAchievementServiceModel;
import com.project.diploma.services.services.AchievementService;
import com.project.diploma.services.services.UserService;
import com.project.diploma.services.services.ValidationService;
import com.project.diploma.web.models.DetailsAchievementModel;
import com.project.diploma.web.models.ProfileUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AchievementServiceImpl implements AchievementService {

    private final ValidationService validationService;
    private final AchievementRepository achievementRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public AchievementServiceImpl(ValidationService validationService,
                                  AchievementRepository achievementRepository,
                                  ModelMapper mapper, UserRepository userRepository, UserService userService) {
        this.validationService = validationService;
        this.achievementRepository = achievementRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public boolean createAchievement(CreateAchievementServiceModel serviceModel) {
        if (validationService.isValidAchievementName(serviceModel)) {
            return false;
        }
        achievementRepository.saveAndFlush(mapper.map(serviceModel, Achievement.class));
        return true;
    }

    @Override
    public List<DetailsAchievementModel> getAchievements(String username) {
        List<Achievement> achievements = achievementRepository.findAll();

        User user = userRepository.findUserByUsername(username);
        ProfileUserModel details = userService.getDetailsForUser(username);
        List<DetailsAchievementModel> models = new ArrayList<>();
        DetailsAchievementModel model;

        if (user.getAchievements().size() == 0) {
            for (Achievement a : achievements) {
                String[] name = a.getName().split(" ");
                model = mapper.map(a, DetailsAchievementModel.class);
                int currentResult = 0;
                if (name[2].equals("battles")) {
                    currentResult = getCurrentResult(name,
                            currentResult,
                            details.getTotalBattlesWithBot(),
                            details.getTotalBattlesWithFriend(),
                            details.getTotalBattlesWithPlayer());
                } else {
                    currentResult = getCurrentResult(name,
                            currentResult,
                            details.getTotalWinsVSBot(),
                            details.getTotalWinsVSFriend(),
                            details.getTotalWinsVSPlayer());
                }
                model.setCurrentResult(currentResult);
                models.add(model);
            }
        } else {
            Set<String> namesOfAchievement = new HashSet<>();
            for (Achievement a : user.getAchievements()) {
                namesOfAchievement.add(a.getName());
            }
            for (Achievement a : achievements) {
                if (namesOfAchievement.contains(a.getName())) {
                    model = mapper.map(a, DetailsAchievementModel.class);
                    model.setComplete(true);
                    model.setCurrentResult(model.getTarget());
                } else {
                    String[] name = a.getName().split(" ");
                    model = mapper.map(a, DetailsAchievementModel.class);
                    int currentResult = 0;
                    if (name[2].equals("battles")) {
                        currentResult = getCurrentResult(name,
                                currentResult,
                                details.getTotalBattlesWithBot(),
                                details.getTotalBattlesWithFriend(),
                                details.getTotalBattlesWithPlayer());
                    } else {
                        currentResult = getCurrentResult(name,
                                currentResult,
                                details.getTotalWinsVSBot(),
                                details.getTotalWinsVSFriend(),
                                details.getTotalWinsVSPlayer());
                    }
                    model.setCurrentResult(currentResult);
                }
                models.add(model);
            }
        }
        return models
                .stream()
                .sorted((a, b) -> {
                    if (a.isComplete()) {
                        if (a.isComplete() && b.isComplete()) {
                            int aTarget = a.getTarget() - a.getCurrentResult();
                            int bTarget = b.getTarget() - b.getCurrentResult();
                            return Integer.compare(bTarget, aTarget);
                        } else {
                            return 1;
                        }
                    } else if (b.isComplete()) {
                        return -1;
                    } else {
                        int aTarget = a.getTarget() - a.getCurrentResult();
                        int bTarget = b.getTarget() - b.getCurrentResult();
                        return Integer.compare(aTarget, bTarget);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public void takePrizeAndAddToUser(String username, String achievementName) {
        Achievement achievement = achievementRepository.getAchievementByName(achievementName);
        User user = userRepository.findUserByUsername(username);

        user.setGold(user.getGold() + achievement.getPrize());
        user.getAchievements().add(achievement);
        achievement.getUsers().add(user);
        userRepository.saveAndFlush(user);
        achievementRepository.saveAndFlush(achievement);
    }

    private int getCurrentResult(String[] name, int currentResult,
                                 Integer bot, Integer friend, Integer player) {
        if (name.length == 3) {
            currentResult = getCurrentResultIfLengthIsThree(bot, friend, player);
        } else {
            currentResult = getCurrentResultIfLengthIsFive(name, currentResult, bot, friend, player);
        }
        return currentResult;
    }

    private int getCurrentResultIfLengthIsFive(String[] name, int currentResult,
                                               Integer bot, Integer friend, Integer player) {
        switch (name[4]) {
            case "bot":
                currentResult = bot;
                break;
            case "friend":
                currentResult = friend;
                break;
            case "player":
                currentResult = player;
                break;
        }
        return currentResult;
    }

    private int getCurrentResultIfLengthIsThree(Integer bot, Integer friend, Integer player) {
        return bot + friend + player;
    }
}
