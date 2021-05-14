package com.project.diploma.service.services;

import com.project.diploma.service.models.CreateHeroServiceModel;
import com.project.diploma.service.models.DetailsHeroServiceModel;
import com.project.diploma.web.models.HeroModel;

import java.util.List;
import java.util.Optional;

public interface HeroService {

    void createHero(CreateHeroServiceModel model, String name) throws Exception;

    DetailsHeroServiceModel findHero(String heroName);

    List<DetailsHeroServiceModel> getAllWithoutCurrent(String heroName);

    String fight(String heroName, String guest);

    HeroModel findNameAndGenderHero(String name);

    DetailsHeroServiceModel getByUsername(String username);
}
