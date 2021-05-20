package com.project.diploma.service.services.implementation;

import com.project.diploma.data.models.Hero;
import com.project.diploma.data.models.Item;
import com.project.diploma.data.repository.HeroRepository;
import com.project.diploma.data.repository.ItemRepository;
import com.project.diploma.service.models.CreateItemServiceModel;
import com.project.diploma.service.services.ItemService;
import com.project.diploma.service.services.ValidationService;
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
        if (model == null){
            throw new Exception("Model does not exists");
        }

        if (validationService.isValidItemName(model)){
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

//    @Override
//    public void addItem(String heroName, String itemName) throws Exception {
//        Optional<Hero> hero1 = heroRepository.findHeroByName(heroName);
//        Item item = itemRepository.findByName(itemName);
//
//        Hero hero = hero1.get();
//        String slot = item.getSlot().name();
//
//
//        boolean hasItem = false;
//        for (Item i: hero.getItems()) {
//            if (i.getSlot().name().equals(slot)){
//                hasItem = true;
//                break;
//            }
//        }
//
//        if (!hasItem){
//            hero.setStamina(hero.getStamina() + item.getStamina());
//            hero.setStrength(hero.getStrength() + item.getStrength());
//            hero.setAttack(hero.getAttack() + item.getAttack());
//            hero.setDefence(hero.getDefence() + item.getDefence());
//            hero.getItems().add(item);
//            item.getHeroes().add(hero);
//            heroRepository.saveAndFlush(hero);
//            itemRepository.saveAndFlush(item);
//        }
//    }
}
