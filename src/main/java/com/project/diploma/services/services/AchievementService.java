package com.project.diploma.services.services;

import com.project.diploma.services.models.CreateAchievementServiceModel;
import com.project.diploma.web.models.DetailsAchievementModel;

import java.util.List;

public interface AchievementService {
    boolean createAchievement(CreateAchievementServiceModel serviceModel);

    List<DetailsAchievementModel> getAchievements(String username);

    void takePrizeAndAddToUser(String username, String achievementName);
}
