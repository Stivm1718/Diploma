package com.project.diploma.services.services.implementation;

import com.project.diploma.data.models.Hero;
import com.project.diploma.data.models.Item;
import com.project.diploma.data.models.User;
import com.project.diploma.data.repositories.HeroRepository;
import com.project.diploma.data.repositories.ItemRepository;
import com.project.diploma.services.models.CreateItemServiceModel;
import com.project.diploma.services.services.ItemService;
import com.project.diploma.services.services.ValidationService;
import com.project.diploma.web.models.ViewItemModel;
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

    @Autowired
    public ItemServiceImpl(ModelMapper mapper, ItemRepository itemRepository, HeroRepository heroRepository, ValidationService validationService) {
        this.mapper = mapper;
        this.itemRepository = itemRepository;
        this.heroRepository = heroRepository;
        this.validationService = validationService;
    }

    @Override
    public boolean create(CreateItemServiceModel model) throws Exception {
        if (model == null) {
            throw new Exception("Model does not exists");
        }

        if (validationService.isValidItemName(model)) {
            return false;
        }
        itemRepository.saveAndFlush(this.mapper.map(model, Item.class));
        return true;
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
    public boolean addItemToHero(String heroName, String itemName) throws Exception {
        Hero hero = heroRepository.findHeroByName(heroName);
        User user = hero.getUser();
        Item item = itemRepository.findByName(itemName);

        if (user.getAuthorities().size() == 2){
            insertItemAndHeroInDatabase(hero, item);
            return true;
        } else {
            if (item.getPrice() <= user.getGold()){
                insertItemAndHeroInDatabase(hero, item);
                user.setGold(user.getGold() - item.getPrice());
                return true;
            } else {
                return false;
            }
        }
    }

    private void insertItemAndHeroInDatabase(Hero hero, Item item) {
        hero.getItems().add(item);
        item.getHeroes().add(hero);
        heroRepository.saveAndFlush(hero);
        itemRepository.saveAndFlush(item);
    }
}
