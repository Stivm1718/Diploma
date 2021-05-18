package com.project.diploma.service.services;

import com.project.diploma.data.models.Hero;
import com.project.diploma.service.models.CreateHeroServiceModel;

import java.util.List;

public interface HeroService {

    void createHero(CreateHeroServiceModel model, String name) throws Exception;

    //DetailsHeroServiceModel findHero(String heroName);

    //List<DetailsHeroServiceModel> getAllWithoutCurrent(String heroName);

    //String fight(String heroName, String guest);

    //HeroModel findNameAndGenderHero(String name);

    //DetailsHeroServiceModel getByUsername(String username);

    //String getHeroNameByUsername(String username);

    long getCountOfHeroes(String username);

    List<Hero> getAllUserHeroes(String username);

    Hero selectOpponent(String username, String heroName);
}
