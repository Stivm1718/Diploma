package com.project.diploma.service.services.implementation;

import com.project.diploma.data.models.Hero;
import com.project.diploma.data.models.Item;
import com.project.diploma.data.repository.HeroRepository;
import com.project.diploma.data.repository.ItemRepository;
import com.project.diploma.service.models.CreateItemServiceModel;
import com.project.diploma.service.services.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ModelMapper mapper;
    private final ItemRepository itemRepository;
    private final HeroRepository heroRepository;

    public ItemServiceImpl(ModelMapper mapper, ItemRepository itemRepository, HeroRepository heroRepository) {
        this.mapper = mapper;
        this.itemRepository = itemRepository;
        this.heroRepository = heroRepository;
    }

//    @Override
//    public void create(CreateItemServiceModel model) throws Exception {
//        if (model == null){
//            throw new Exception("Model does not exists");
//        }
//        itemRepository.saveAndFlush(this.mapper.map(model, Item.class));
//    }

//    @Override
//    public List<CreateItemServiceModel> getByHeroName(String heroName) {
//        return this.itemRepository.findAll()
//                .stream()
//                .filter(i -> !i.getHeroes().stream()
//                .map(Hero::getName)
//                .collect(Collectors.toList()).contains(heroName))
//                .map(u -> this.mapper.map(u, CreateItemServiceModel.class))
//                .collect(Collectors.toList());
//    }

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
