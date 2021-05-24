package com.project.diploma.services.services.implementation;

import com.project.diploma.data.models.*;
import com.project.diploma.data.repositories.HeroRepository;
import com.project.diploma.data.repositories.ItemRepository;
import com.project.diploma.errors.HeroNotFoundException;
import com.project.diploma.services.models.CreateItemServiceModel;
import com.project.diploma.services.services.HeroService;
import com.project.diploma.services.services.ItemService;
import com.project.diploma.services.services.ValidationService;
import com.project.diploma.web.models.DetailsHeroModel;
import com.project.diploma.web.models.ShowItemsHero;
import com.project.diploma.web.models.ViewItemModel;
import com.project.diploma.web.models.ViewItemModelWithTypePay;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ModelMapper mapper;
    private final ItemRepository itemRepository;
    private final HeroRepository heroRepository;
    private final ValidationService validationService;
    private final HeroService heroService;

    @Autowired
    public ItemServiceImpl(ModelMapper mapper, ItemRepository itemRepository, HeroRepository heroRepository, ValidationService validationService, HeroService heroService) {
        this.mapper = mapper;
        this.itemRepository = itemRepository;
        this.heroRepository = heroRepository;
        this.validationService = validationService;
        this.heroService = heroService;
    }

    @Override
    public boolean create(CreateItemServiceModel model) throws Exception {
        if (model == null) {
            throw new Exception("Model does not exists");
        }

        if (validationService.isValidItemName(model)) {
            return false;
        }

        if ((model.getPriceInGold() == null && model.getPriceInMoney() == null) ||
                (model.getPriceInGold() != null && model.getPriceInMoney() != null)) {
            return false;
        }

        itemRepository.saveAndFlush(mapper.map(model, Item.class));
        return true;
    }


    @Override
    public boolean addItemToHero(String heroName, String itemName) throws Exception {
        Hero hero = heroRepository.findHeroByName(heroName);
        User user = hero.getUser();
        Item item = itemRepository.findByName(itemName);

        if (user.getAuthorities().size() == 2) {
            insertItemAndHeroInDatabase(hero, item);
            return true;
        }
//        else {
//            if (item.getPrice() <= user.getGold()) {
//                insertItemAndHeroInDatabase(hero, item);
//                user.setGold(user.getGold() - item.getPrice());
//                return true;
//            } else {
//                return false;
//            }
//        }
        return true;
    }

    @Override
    public ShowItemsHero getItemsOfHero(String heroName) {
        Hero hero = heroRepository.findHeroByName(heroName);

        if (hero == null) {
            throw new HeroNotFoundException("No such found");
        }

        DetailsHeroModel details = heroService.detailsHero(heroName);
        return mapper.map(details, ShowItemsHero.class);
    }

    @Override
    public List<ViewItemModel> takeAllItemsThatAreNotThere(String heroName) {
        return this.itemRepository.findAll()
                .stream()
                .filter(i -> !i.getHeroes().stream()
                        .map(Hero::getName)
                        .collect(Collectors.toList()).contains(heroName))
                .map(u -> this.mapper.map(u, ViewItemModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewItemModelWithTypePay> takeItemWithGoldForPay(String heroName) {
        return itemRepository
                .findAll()
                .stream()
                .filter(i -> i.getBuy().equals(Buy.GOLD))
                .filter(i -> !i.getHeroes().stream()
                        .map(Hero::getName)
                        .collect(Collectors.toList()).contains(heroName))
                .map(u -> this.mapper.map(u, ViewItemModelWithTypePay.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewItemModelWithTypePay> takeItemWithMoneyForPay(String heroName) {
        return itemRepository
                .findAll()
                .stream()
                .filter(i -> i.getBuy().equals(Buy.MONEY))
                .filter(i -> !i.getHeroes().stream()
                        .map(Hero::getName)
                        .collect(Collectors.toList()).contains(heroName))
                .map(u -> this.mapper.map(u, ViewItemModelWithTypePay.class))
                .collect(Collectors.toList());
    }

    private void insertItemAndHeroInDatabase(Hero hero, Item item) {
        hero.getItems().add(item);
        item.getHeroes().add(hero);
        heroRepository.saveAndFlush(hero);
        itemRepository.saveAndFlush(item);
    }
}
