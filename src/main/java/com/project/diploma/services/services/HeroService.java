package com.project.diploma.services.services;

import com.project.diploma.services.models.CreateHeroServiceModel;
import com.project.diploma.web.models.*;

import java.util.List;

public interface HeroService {

    boolean createHero(CreateHeroServiceModel model, String name) throws Exception;

    DetailsHeroModel detailsHero(String heroName);

    long getCountOfHeroes(String username);

    List<HeroPictureModel> getAllUserHeroes(String username);

    HeroPictureModel selectOpponent(String username, String heroName);

    HeroPictureModel getMyHero(String name);

    BattleModel fight(HeroModel myHero, HeroModel opponent, SelectItemsModel myItems, SelectItemsModel opponentItems);
}
