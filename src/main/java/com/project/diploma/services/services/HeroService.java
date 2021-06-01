package com.project.diploma.services.services;

import com.project.diploma.services.models.CreateHeroServiceModel;
import com.project.diploma.web.models.*;

import java.util.List;

public interface HeroService {

    boolean createHero(CreateHeroServiceModel model, String name) throws Exception;

    DetailsHeroModel detailsHero(String heroName);

    int getCountOfHeroes(String username);

    List<HeroPictureModel> getAllUserHeroes(String username);

    HeroPictureModel selectOpponent(String username, String heroName);

    HeroPictureModel getMyHero(String name);

    BattleModel fightWithPlayer(HeroPictureModel myHero, HeroPictureModel opponent, SelectItemsModel myItems, SelectItemsModel opponentItems);

    List<RankingModel> getSortedHeroes();

    int sellItem(String nameHero, String nameItem);

    HeroPictureModel selectBot(String name);

    BotModel fightWithBot(HeroPictureModel myHero, HeroPictureModel bot, SelectItemsModel myItems, SelectItemsModel itemsOfBot);
}
