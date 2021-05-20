package com.project.diploma.service.services;

import com.project.diploma.data.models.Hero;
import com.project.diploma.service.models.CreateHeroServiceModel;
import com.project.diploma.service.models.DetailsHeroModel;
import com.project.diploma.web.models.HeroModel;

import java.util.List;

public interface HeroService {

    boolean createHero(CreateHeroServiceModel model, String name) throws Exception;

    DetailsHeroModel detailsHero(String heroName);

    //List<DetailsHeroServiceModel> getAllWithoutCurrent(String heroName);

    //String fight(String heroName, String guest);

    //HeroModel findNameAndGenderHero(String name);

    //DetailsHeroServiceModel getByUsername(String username);

    //String getHeroNameByUsername(String username);

    long getCountOfHeroes(String username);

    List<Hero> getAllUserHeroes(String username);

    HeroModel selectOpponent(String username, String heroName);

    HeroModel getHero(String name);
}
