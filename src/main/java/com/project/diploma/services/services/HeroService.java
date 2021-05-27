package com.project.diploma.services.services;

import com.project.diploma.data.models.Hero;
import com.project.diploma.services.models.CreateHeroServiceModel;
import com.project.diploma.web.models.BattleModel;
import com.project.diploma.web.models.DetailsHeroModel;
import com.project.diploma.web.models.HeroModel;
import com.project.diploma.web.models.SelectItemsModel;

import java.util.List;

public interface HeroService {

    boolean createHero(CreateHeroServiceModel model, String name) throws Exception;

    DetailsHeroModel detailsHero(String heroName);

    long getCountOfHeroes(String username);

    List<Hero> getAllUserHeroes(String username);

    HeroModel selectOpponent(String username, String heroName);

    HeroModel getMyHero(String name);

    BattleModel fight(HeroModel myHero, HeroModel opponent, SelectItemsModel myItems, SelectItemsModel opponentItems);
}
